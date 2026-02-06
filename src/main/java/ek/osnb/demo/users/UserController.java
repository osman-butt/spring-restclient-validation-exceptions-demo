package ek.osnb.demo.users;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse newUser = userService.createUser(request);
        return ResponseEntity.created(URI.create("/api/users/" + newUser.id())).body(newUser);
    }

    @GetMapping("/{id}/todos")
    public ResponseEntity<UserWithTodos> getUserByIdWithTodos(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserByIdWithTodos(id));
    }
}
