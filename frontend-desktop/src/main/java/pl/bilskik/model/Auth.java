package pl.bilskik.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Auth {
    private String jwt;
    public Auth(String jwt) {
        this.jwt = jwt;
    }
    public Auth() {}
}
