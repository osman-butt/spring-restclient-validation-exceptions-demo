package ek.osnb.demo.todos;

public record TodoResponse(Long userId, Long id, String title, boolean completed) {
}

//{
//  "userId": 1,
//  "id": 1,
//  "title": "delectus aut autem",
//  "completed": false
//}