package pl.bilskik.lab34.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponse {
    private String jwt;
    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }
}
