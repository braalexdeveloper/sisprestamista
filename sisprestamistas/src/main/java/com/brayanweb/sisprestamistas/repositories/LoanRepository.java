package com.brayanweb.sisprestamistas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brayanweb.sisprestamistas.models.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

}
