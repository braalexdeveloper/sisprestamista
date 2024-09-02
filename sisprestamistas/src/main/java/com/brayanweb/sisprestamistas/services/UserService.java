package com.brayanweb.sisprestamistas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.brayanweb.sisprestamistas.dtos.UserRequest;
import com.brayanweb.sisprestamistas.dtos.UserResponse;
import com.brayanweb.sisprestamistas.exceptions.ResourceNotFoundException;
import com.brayanweb.sisprestamistas.models.Role;
import com.brayanweb.sisprestamistas.models.User;
import com.brayanweb.sisprestamistas.repositories.RoleRepository;
import com.brayanweb.sisprestamistas.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public UserResponse create(UserRequest userRequest) {
        Role role = roleRepository.findById(userRequest.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con id: " + userRequest.getRoleId()));

        User user = this.convertToUser(userRequest);
        user.setRole(role);

        User userCreated = userRepository.save(user);
        return this.convertToUserResponse(userCreated);
    }

    public UserResponse update(Long id, UserRequest userRequest) {

        User userFound = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con el id : " + id));

        Role role = roleRepository.findById(userRequest.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con id: " + userRequest.getRoleId()));

        updateUserFromRequest(userFound, userRequest, role);

        User userUpdated = userRepository.save(userFound);

        return convertToUserResponse(userUpdated);
    }

    public String delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado con id : " + id);
        }
        userRepository.deleteById(id);
        return "Usuario eliminado!";
    }

    private void updateUserFromRequest(User user, UserRequest userRequest, Role role) {
        user.setName(userRequest.getName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setRole(role);
    }

    public User convertToUser(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return user;
    }

    public UserResponse convertToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setName(user.getName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setRoleName(user.getRole().getName());
        return userResponse;
    }

}
