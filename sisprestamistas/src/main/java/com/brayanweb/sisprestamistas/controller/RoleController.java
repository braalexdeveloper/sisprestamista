package com.brayanweb.sisprestamistas.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.brayanweb.sisprestamistas.models.Role;
import com.brayanweb.sisprestamistas.services.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = roleService.getRoles();

        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRole(@PathVariable("id") Long id) {
        Optional<Role> role = roleService.getRole(id);
        return ResponseEntity.ok(role.get());
    }

    @PostMapping
    public ResponseEntity<Role> create(@Valid @RequestBody Role role) {
        Role createdRole = roleService.create(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> update(@PathVariable("id") Long id, @Valid @RequestBody Role role) {
        Role updatedRole = roleService.update(id, role);
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable("id") Long id) {
        roleService.delete(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Rol eliminado correctamente");
        return ResponseEntity.ok(response);
    }

}
