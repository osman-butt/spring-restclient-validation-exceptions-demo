package ek.osnb.demo.todos;

public record TodoRequest(Long userId, String title, boolean completed) {
}
