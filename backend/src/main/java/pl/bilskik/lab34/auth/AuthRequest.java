package pl.bilskik.lab34.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {
    @NotBlank(message = "Username cannot be blank!")
    private String username;
    @NotBlank(message = "Password cannot be blank!")
    private String password;
}
