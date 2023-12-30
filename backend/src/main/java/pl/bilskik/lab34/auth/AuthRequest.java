package pl.bilskik.lab34.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthRequest {
    private String username;
    private String password;
    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
