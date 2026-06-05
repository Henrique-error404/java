package br.com.fiap.terraorbit.controller;

import br.com.fiap.terraorbit.dto.JwtResponse;
import br.com.fiap.terraorbit.dto.LoginDTO;
import br.com.fiap.terraorbit.dto.RegisterDTO;
import br.com.fiap.terraorbit.entity.User;
import br.com.fiap.terraorbit.exception.EmailAlreadyExists;
import br.com.fiap.terraorbit.repository.UserRepo;
import br.com.fiap.terraorbit.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserRepo userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService,
                          AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public String register(@RequestBody @Valid RegisterDTO dto) {

        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new EmailAlreadyExists("Email já cadastrado");
        }

        User user = User.builder()
                .name(dto.name())
                .email(dto.email())
                .passwordHash(passwordEncoder.encode(dto.password()))
                .build();

        userRepository.save(user);

        return jwtService.generateToken(user.getEmail());
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginDTO dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.email(),
                        dto.password()
                )
        );

        return new JwtResponse(jwtService.generateToken(dto.email()));
    }

}