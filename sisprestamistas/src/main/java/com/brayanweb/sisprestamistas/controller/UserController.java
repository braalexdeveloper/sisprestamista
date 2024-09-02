package com.brayanweb.sisprestamistas.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brayanweb.sisprestamistas.dtos.UserRequest;
import com.brayanweb.sisprestamistas.dtos.UserResponse;
import com.brayanweb.sisprestamistas.exceptions.ResourceNotFoundException;
import com.brayanweb.sisprestamistas.models.User;
import com.brayanweb.sisprestamistas.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("users")

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        User user = userService.getUser(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con el id: " + id));

        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.create(userRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("user", userResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@Valid @PathVariable("id") Long id, @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.update(id, userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable("id") Long id) {
        String deleteResponse = userService.delete(id);
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", deleteResponse);
        return ResponseEntity.ok(response);
    }

}
