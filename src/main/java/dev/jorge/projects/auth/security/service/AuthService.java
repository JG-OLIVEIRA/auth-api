package dev.jorge.projects.auth.security.service;

import dev.jorge.projects.auth.security.config.TokenConfig;

import dev.jorge.projects.auth.user.entity.User;
import dev.jorge.projects.auth.user.enums.Role;
import dev.jorge.projects.auth.user.exception.UserAlreadyExistsException;
import dev.jorge.projects.auth.user.exception.UserNotFoundException;
import dev.jorge.projects.auth.user.repository.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            TokenConfig tokenConfig
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenConfig = tokenConfig;
    }

    @Transactional(readOnly = true)
    public String signIn(String email, String password) {
        User user = findUserByEmail(email);
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(email, password);
        if(authenticationManager.authenticate(userAndPass).isAuthenticated()){
            return tokenConfig.generateSessionToken(user);
        }
        throw new BadCredentialsException("Credenciais inválidas");
    }

    @Transactional
    public User signUp(String firstName, String lastName, String username, String email, String password) {

        if(userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }

        if(userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException(username);
        }

        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(hashPassword(password));
        newUser.setRoles(Set.of(Role.ROLE_USER));

        return userRepository.save(newUser);
    }

    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}