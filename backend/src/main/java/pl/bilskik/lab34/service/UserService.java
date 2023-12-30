package pl.bilskik.lab34.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.bilskik.lab34.auth.AuthRequest;
import pl.bilskik.lab34.auth.AuthResponse;
import pl.bilskik.lab34.auth.JwtUtil;
import pl.bilskik.lab34.entity.User;
import pl.bilskik.lab34.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(
            JwtUtil jwtUtil,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager
    ) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }
    public AuthResponse register(AuthRequest authRequest) {
        if(authRequest == null || authRequest.getUsername().equals("") || authRequest.getPassword().equals("")) {
            throw new UsernameNotFoundException("Invalid authRequest!");
        }
        User user = User.builder()
                .username(authRequest.getUsername())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .build();
        AuthResponse authResponse = AuthResponse.builder()
                .jwt(jwtUtil.generateJWT(user))
                .build();
        userRepository.save(user);
        return authResponse;
    }

    public AuthResponse login(AuthRequest authRequest) {
        System.out.println(authRequest.getUsername() + " " + authRequest.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        Optional<User> user = userRepository.findByUsername(authRequest.getUsername());
        if(user.isEmpty()) {
            throw new NoSuchElementException("User is empty!");
        }
        AuthResponse authResponse = AuthResponse.builder()
                .jwt(jwtUtil.generateJWT(user.get()))
                .build();
        return authResponse;
    }
}
