package com.brayanweb.sisprestamistas.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brayanweb.sisprestamistas.dtos.ClientRequest;
import com.brayanweb.sisprestamistas.dtos.ClientResponse;
import com.brayanweb.sisprestamistas.services.ClientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getClients() {

        List<ClientResponse> clients = clientService.getClients();

        return ResponseEntity.ok(createResponse("success", "clients", clients));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getClients(@PathVariable("id") Long id) {

        ClientResponse client = clientService.getClient(id);

        return ResponseEntity.ok(createResponse("success", "client", client));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody ClientRequest clientRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMap(result));
        }

        ClientResponse client = clientService.create(clientRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResponse("success", "client", client));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("id") Long id, @Valid @RequestBody ClientRequest clientRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMap(result));
        }

        ClientResponse client = clientService.update(clientRequest, id);
        return ResponseEntity.ok(createResponse("success", "client", client));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id) {
        String message = clientService.delete(id);
        return ResponseEntity.ok(createResponse("success", "message", message));
    }

    private Map<String, Object> createResponse(String status, String key, Object data) {
        Map<String, Object> response = new HashMap<>();

        response.put("status", status);
        response.put(key, data);
        return response;
    }

    private Map<String, Object> createErrorMap(BindingResult result) {
        Map<String, Object> errors = new HashMap<>();

        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return errors;
    }

}
