package com.brayanweb.sisprestamistas.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brayanweb.sisprestamistas.dtos.PaymentRequest;
import com.brayanweb.sisprestamistas.dtos.PaymentResponse;
import com.brayanweb.sisprestamistas.services.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getPayments() {
        List<PaymentResponse> payments = paymentService.getPayments();
        return ResponseEntity.ok(createMapResponse("success", "payments", payments));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPayment(@PathVariable("id") Long id) {
        PaymentResponse payment = paymentService.getPayment(id);
        return ResponseEntity.ok(createMapResponse("success", "payment", payment));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> create(@Valid @ModelAttribute PaymentRequest paymentRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createMapError(result));
        }
        PaymentResponse payment = paymentService.create(paymentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createMapResponse("success", "payment", payment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@Valid @ModelAttribute PaymentRequest paymentRequest, @PathVariable("id") Long id, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createMapError(result));
        }
        PaymentResponse payment = paymentService.update(paymentRequest, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(createMapResponse("success", "payment", payment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id) {
        String response = paymentService.delete(id);
        return ResponseEntity.ok(createMapResponse("success", "message", response));
    }

    private Map<String, Object> createMapResponse(String status, String key, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put(key, data);
        return response;

    }

    private Map<String, Object> createMapError(BindingResult result) {
        Map<String, Object> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return errors;

    }

}
