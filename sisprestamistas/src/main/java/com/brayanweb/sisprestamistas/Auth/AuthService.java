package com.brayanweb.sisprestamistas.Auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.brayanweb.sisprestamistas.Jwt.JwtService;
import com.brayanweb.sisprestamistas.models.Role;
import com.brayanweb.sisprestamistas.models.User;
import com.brayanweb.sisprestamistas.repositories.RoleRepository;
import com.brayanweb.sisprestamistas.repositories.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .userName(user.getName())
                .role(user.getRole().getName())
                .build();
    }

    public RegisterResponse register(RegisterRequest request) {
        //verifica si el rol existe
        Role role = roleRepository.findByName("Client").orElseThrow(() -> new RuntimeException("El ro lno existe"));

        //Verifica si el email ya existe
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya existe!");
        }

        User user = User.builder()
                .name(request.getUserName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);
        return new RegisterResponse("Usuario Registrado con éxito");

    }
}
