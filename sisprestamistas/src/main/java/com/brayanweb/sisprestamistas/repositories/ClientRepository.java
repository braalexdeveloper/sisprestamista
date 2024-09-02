package com.brayanweb.sisprestamistas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brayanweb.sisprestamistas.models.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
