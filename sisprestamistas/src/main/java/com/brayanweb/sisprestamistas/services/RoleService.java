package com.brayanweb.sisprestamistas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.brayanweb.sisprestamistas.exceptions.ResourceNotFoundException;
import com.brayanweb.sisprestamistas.models.Role;
import com.brayanweb.sisprestamistas.repositories.RoleRepository;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRole(Long id) {
        return roleRepository.findById(id);
    }

    public Role create(Role role) {
        return roleRepository.save(role);
    }

    public Role update(Long id, Role role) {
        return roleRepository.findById(id)
                .map(foundRole -> {
                    foundRole.setName(role.getName());
                    foundRole.setDescription(role.getDescription());
                    return roleRepository.save(foundRole);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con id " + id));

    }

    public void delete(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role not found with id " + id);
        }
        roleRepository.deleteById(id);

    }

    public Role createRoleIfNotFound(String roleName) {
        if (!roleRepository.existsByName(roleName)) {
            Role role = new Role();
            role.setName(roleName);
            return roleRepository.save(role);
        }
        return null;
    }

}
