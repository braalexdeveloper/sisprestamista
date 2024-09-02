package com.brayanweb.sisprestamistas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.brayanweb.sisprestamistas.dtos.UserRequest;
import com.brayanweb.sisprestamistas.models.Role;
import com.brayanweb.sisprestamistas.repositories.UserRepository;
import com.brayanweb.sisprestamistas.services.RoleService;
import com.brayanweb.sisprestamistas.services.UserService;

@SpringBootApplication
public class SisprestamistasApplication implements CommandLineRunner {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(SisprestamistasApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Role role = roleService.createRoleIfNotFound("Admin");
        roleService.createRoleIfNotFound("Client");

        if (!userRepository.findByEmail("brayan@gmail.com").isPresent()) {
            UserRequest user = new UserRequest();
            user.setName("brayan");
            user.setLastName("quispe rivas");
            user.setEmail("brayan@gmail.com");
            user.setPassword("brayan123");
            user.setRoleId(role.getId());
            userService.create(user);
        }

    }

}
