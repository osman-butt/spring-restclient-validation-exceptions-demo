package ek.osnb.demo.todos;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodosController {
    private final TodosClient todosClient;

    public TodosController(TodosClient todosClient) {
        this.todosClient = todosClient;
    }

    @GetMapping
    public List<TodoResponse> getAllTodos() {
        return todosClient.getTodos();
    }

    @GetMapping("/{id}")
    public TodoResponse getTodoById(@PathVariable Long id) {
        return todosClient.getTodoById(id);
    }

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@RequestBody TodoRequest todoRequest) {
        TodoResponse newTodo = todosClient.createTodo(todoRequest);
        return ResponseEntity.created(URI.create("/api/todos/"+newTodo.id())).body(newTodo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todosClient.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}
