package ek.osnb.demo.todos;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

@HttpExchange("/todos")
public interface TodosClient {

    @GetExchange
    List<TodoResponse> getTodos();

    @GetExchange("/{id}")
    TodoResponse getTodoById(@PathVariable Long id);

    @PostExchange
    TodoResponse createTodo(@RequestBody TodoRequest todoRequest);

    @DeleteExchange("/{id}")
    void deleteTodo(@PathVariable Long id);
}
