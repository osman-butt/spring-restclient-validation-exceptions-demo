package ek.osnb.demo.users;

import java.util.List;

public record UserWithTodos(Long id, String name, String email, List<UserTodo> todos) {
}

record UserTodo(Long id, String title, boolean completed) {}