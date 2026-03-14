package com.example.todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final List<Todo> todos = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    @GetMapping
    public List<Todo> listTodos() {
        return todos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTodo(@PathVariable long id) {
        return todos.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Todo not found")));
    }

    @PostMapping
    public ResponseEntity<Object> createTodo(@RequestBody Map<String, Object> body) {
        String title = (String) body.get("title");
        if (title == null || title.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Title is required"));
        }

        Todo todo = new Todo(nextId.getAndIncrement(), title);
        todos.add(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(todo);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateTodo(@PathVariable long id,
                                             @RequestBody Map<String, Object> body) {
        return todos.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .<ResponseEntity<Object>>map(todo -> {
                    if (body.containsKey("title")) {
                        todo.setTitle((String) body.get("title"));
                    }
                    if (body.containsKey("done")) {
                        todo.setDone((Boolean) body.get("done"));
                    }
                    return ResponseEntity.ok(todo);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Todo not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable long id) {
        boolean removed = todos.removeIf(t -> t.getId() == id);
        if (!removed) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
