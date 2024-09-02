package com.brayanweb.sisprestamistas.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.brayanweb.sisprestamistas.dtos.LoanRequest;
import com.brayanweb.sisprestamistas.dtos.LoanResponse;
import com.brayanweb.sisprestamistas.exceptions.ResourceNotFoundException;
import com.brayanweb.sisprestamistas.models.Loan;
import com.brayanweb.sisprestamistas.models.User;
import com.brayanweb.sisprestamistas.repositories.LoanRepository;
import com.brayanweb.sisprestamistas.repositories.UserRepository;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    public LoanService(LoanRepository loanRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
    }

    public List<LoanResponse> getLoans() {
        List<Loan> loans = loanRepository.findAll();
        List<LoanResponse> loansResponse = new ArrayList<>();

        for (Loan loan : loans) {
            loansResponse.add(convertToLoanResponse(loan));
        }

        return loansResponse;
    }

    public LoanResponse getLoan(Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Loan no encontrado"));

        return convertToLoanResponse(loan);

    }

    public LoanResponse create(LoanRequest loanRequest) {
        Optional<User> optionalUser = userRepository.findById(loanRequest.getUserId());

        if (optionalUser.isPresent()) {

            Loan loan = convertToLoan(loanRequest, new Loan(), optionalUser.get());
            Loan createdLoan = loanRepository.save(loan);

            return convertToLoanResponse(createdLoan);

        } else {
            throw new ResourceNotFoundException("Usuario no encontrado para crear el prestamo!");
        }

    }

    public LoanResponse update(LoanRequest loanRequest, Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontro el prestamo con id : " + id));
        User user = userRepository.findById(loanRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException("No se encontro el usuario"));

        Loan updatedLoan = convertToLoan(loanRequest, loan, user);
        updatedLoan = loanRepository.save(updatedLoan);

        return convertToLoanResponse(updatedLoan);

    }

    public String delete(Long id) {
        if (!loanRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se econtro el prestamos con id " + id);
        }
        loanRepository.deleteById(id);
        return "Prestamo eliminado con éxito!";
    }

    private Loan convertToLoan(LoanRequest loanRequest, Loan loan, User user) {

        loan.setUser(user);
        loan.setAmount(loanRequest.getAmount());
        loan.setInterestRate(loanRequest.getInterestRate());
        loan.setTerm(loanRequest.getTerm());
        if (loan.getId() != null) {
            loan.setUpdatedAt(new Date());
        } else {
            loan.setCreatedAt(new Date());

            loan.setStatus("Aprobado");
        }

        return loan;
    }

    private LoanResponse convertToLoanResponse(Loan createdLoan) {
        LoanResponse loanResponse = new LoanResponse();
        loanResponse.setId(createdLoan.getId());
        loanResponse.setAmount(createdLoan.getAmount());
        loanResponse.setInterestRate(createdLoan.getInterestRate());
        loanResponse.setTerm(createdLoan.getTerm());
        loanResponse.setStatus(createdLoan.getStatus());
        loanResponse.setCreatedAt(createdLoan.getCreatedAt());
        loanResponse.setClientName(createdLoan.getUser().getName());

        return loanResponse;
    }

}
