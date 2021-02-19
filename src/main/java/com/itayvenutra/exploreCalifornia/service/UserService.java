package com.itayvenutra.exploreCalifornia.service;

import com.itayvenutra.exploreCalifornia.domain.Role;
import com.itayvenutra.exploreCalifornia.domain.User;
import com.itayvenutra.exploreCalifornia.repo.RoleRepository;
import com.itayvenutra.exploreCalifornia.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager,
                       RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Authentication signin(String username, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
    public Optional<User> signup(String username, String password, String firstName, String lastName) {
        if (!userRepository.findByUsername(username).isPresent()) {
            Optional<Role> role = roleRepository.findByRoleName("ROLE_CSR");
            return Optional.of(userRepository.save
                    (new User(username,
                            passwordEncoder.encode(password),
                            role.get(),
                            firstName,
                            lastName)));
        }
        return Optional.empty();
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

}
