package pl.bilskik.lab34.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bilskik.lab34.auth.AuthRequest;
import pl.bilskik.lab34.auth.AuthResponse;
import pl.bilskik.lab34.service.UserService;

@RestController
@Slf4j
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String test() {
//        return new ResponseEntity<>(userService.register(authRequest), HttpStatusCode.valueOf(200));
        log.error("DZIALASZ?");
        return "OK";
    }
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(userService.register(authRequest), HttpStatusCode.valueOf(200));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(userService.login(authRequest), HttpStatusCode.valueOf(200));
    }

}
