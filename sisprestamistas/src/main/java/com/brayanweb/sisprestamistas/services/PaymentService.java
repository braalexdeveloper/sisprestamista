package com.brayanweb.sisprestamistas.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.brayanweb.sisprestamistas.dtos.PaymentRequest;
import com.brayanweb.sisprestamistas.dtos.PaymentResponse;
import com.brayanweb.sisprestamistas.exceptions.ResourceNotFoundException;
import com.brayanweb.sisprestamistas.models.Loan;
import com.brayanweb.sisprestamistas.models.Payment;
import com.brayanweb.sisprestamistas.repositories.LoanRepository;
import com.brayanweb.sisprestamistas.repositories.PaymentRepository;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    public final LoanRepository loanRepository;
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/payments/";

    public PaymentService(PaymentRepository paymentRepository, LoanRepository loanRepository) {
        this.paymentRepository = paymentRepository;
        this.loanRepository = loanRepository;
    }

    public List<PaymentResponse> getPayments() {

        List<PaymentResponse> paymentsResponse = paymentRepository.findAll().stream()
                .map(this::convertToPaymentResponse).collect(Collectors.toList());
        return paymentsResponse;
    }

    public PaymentResponse getPayment(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontro el pago"));

        return convertToPaymentResponse(payment);
    }

    @Transactional
    public PaymentResponse create(PaymentRequest paymentRequest) {
        Loan loan = loanRepository.findById(paymentRequest.getLoanId()).orElseThrow(() -> new ResourceNotFoundException("El prestamo no se encontró"));

        Payment payment = paymentRepository.save(convertToPayment(paymentRequest, new Payment(), loan));

        return convertToPaymentResponse(payment);

    }

    @Transactional
    public PaymentResponse update(PaymentRequest paymentRequest, Long id) {
        Payment paymentFound = paymentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontro el pago con ese id"));

        Loan loanFound = loanRepository.findById(paymentFound.getLoan().getId()).orElseThrow(() -> new ResourceNotFoundException("No se encontro el prestamo"));

        if (paymentFound.getReceiptPayment() != null) {
            deleteImage(paymentFound.getReceiptPayment());
        }
        Payment updatedPayment = paymentRepository.save(convertToPayment(paymentRequest, paymentFound, loanFound));
        return convertToPaymentResponse(updatedPayment);
    }

    public String delete(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new ResourceNotFoundException("El pago no existe");
        }
        paymentRepository.deleteById(id);
        return "Pago eliminado";
    }

    private Payment convertToPayment(PaymentRequest paymentRequest, Payment payment, Loan loan) {
        payment.setAmount(paymentRequest.getAmount());
        payment.setReceiptPayment(saveImage(paymentRequest.getReceiptPayment()));
        payment.setLoan(loan);
        return payment;
    }

    private PaymentResponse convertToPaymentResponse(Payment payment) {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setId(payment.getId());
        paymentResponse.setAmount(payment.getAmount());
        paymentResponse.setReceiptPayment(payment.getReceiptPayment());
        paymentResponse.setCreatedAt(payment.getCreatedAt());
        paymentResponse.setLoanId(payment.getLoan().getId());
        paymentResponse.setUserName(payment.getLoan().getUser().getName());

        return paymentResponse;
    }

    private String saveImage(MultipartFile image) {

        try {
            String imageName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path imagePath = Paths.get(UPLOAD_DIR + imageName);

            //Crea el directorio si no existe
            Files.createDirectories(imagePath.getParent());

            Files.write(imagePath, image.getBytes());
            return "/uploads/payments/" + imageName;
        } catch (IOException e) {
            throw new RuntimeException("Error al gaurdar la imagen", e);
        }
    }

    private void deleteImage(String imagePath) {
        try {
            // Elimina el prefijo "/uploads/" de la URL si está presente
            String relativePath = imagePath.startsWith("/uploads/payments/") ? imagePath.substring(18) : imagePath;
            Path path = Paths.get(UPLOAD_DIR + relativePath);

            if (Files.exists(path)) {
                Files.delete(path);
                System.out.println("Imagen eliminada: " + path.toString());
            } else {
                System.out.println("La imagen no existe : " + path.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar la imagen", e);

        }
    }

}
