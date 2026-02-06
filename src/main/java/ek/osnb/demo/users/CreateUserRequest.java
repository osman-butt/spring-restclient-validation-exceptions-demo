package ek.osnb.demo.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank(message = "Name must not be blank")
        @Size(min = 2, max = 150, message = "Name must be between 2 and 150 characters")
        String name,
        @Email
        @NotBlank
        String email
) {
}
