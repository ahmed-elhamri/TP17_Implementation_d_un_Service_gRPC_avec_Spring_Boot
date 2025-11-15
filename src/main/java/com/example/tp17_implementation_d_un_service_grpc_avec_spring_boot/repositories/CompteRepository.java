package com.example.tp17_implementation_d_un_service_grpc_avec_spring_boot.repositories;

import com.example.tp17_implementation_d_un_service_grpc_avec_spring_boot.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepository extends JpaRepository<Compte, String> {
}