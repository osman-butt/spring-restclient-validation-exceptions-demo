package ek.osnb.demo.users;

import ek.osnb.demo.exceptions.NotFoundException;
import ek.osnb.demo.todos.TodoResponse;
import ek.osnb.demo.todos.TodosClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TodosClient todosClient;

    public UserService(UserRepository userRepository, TodosClient todosClient) {
        this.userRepository = userRepository;
        this.todosClient = todosClient;
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserResponse> responses = new ArrayList<>();
        for (var user : users) {
            responses.add(toUserResponse(user));
        }
        return responses;
    }

    public UserResponse getUserById(Long id) {
        return toUserResponse(
                userRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("User not found with id: " + id))
        );
    }

    public UserResponse createUser(CreateUserRequest request) {
        var user = new User(request.name(), request.email());
        User saved = userRepository.save(user);
        return toUserResponse(saved);
    }


    public UserWithTodos getUserByIdWithTodos(Long id) {
        User userById = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));
        List<TodoResponse> todos = todosClient.getTodos();
        List<UserTodo> todosFilteredByUserId = new ArrayList<>();
        for (TodoResponse todo : todos) {
            if (todo.userId().equals(id)) {
                todosFilteredByUserId.add(new UserTodo(todo.id(), todo.title(), todo.completed()));
            }
        }
        return new UserWithTodos(userById.getId(), userById.getName(), userById.getEmail(), todosFilteredByUserId);
    }

    private UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}
