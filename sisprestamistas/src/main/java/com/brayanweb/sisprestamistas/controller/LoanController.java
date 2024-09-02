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

import com.brayanweb.sisprestamistas.dtos.LoanRequest;
import com.brayanweb.sisprestamistas.dtos.LoanResponse;
import com.brayanweb.sisprestamistas.services.LoanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getLoans() {
        List<LoanResponse> loans = loanService.getLoans();
        return ResponseEntity.ok(createResponse("success", "loans", loans));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getLoan(@PathVariable("id") Long id) {
        LoanResponse loan = loanService.getLoan(id);
        return ResponseEntity.ok(createResponse("success", "loan", loan));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody LoanRequest loanRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMap(result));
        }

        LoanResponse loan = loanService.create(loanRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResponse("success", "loan", loan));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("id") Long id, @Valid @RequestBody LoanRequest loanRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMap(result));
        }
        LoanResponse loan = loanService.update(loanRequest, id);
        return ResponseEntity.ok(createResponse("success", "loan", loan));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id) {
        String responseLoan = loanService.delete(id);
        return ResponseEntity.ok(createResponse("success", "message", responseLoan));
    }

    private Map<String, Object> createResponse(String status, String key, Object value) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put(key, value);
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
