package com.brayanweb.sisprestamistas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brayanweb.sisprestamistas.models.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
