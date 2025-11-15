package com.example.tp17_implementation_d_un_service_grpc_avec_spring_boot;

import com.example.tp17_implementation_d_un_service_grpc_avec_spring_boot.entities.Compte;
import com.example.tp17_implementation_d_un_service_grpc_avec_spring_boot.entities.TypeCompte;
import com.example.tp17_implementation_d_un_service_grpc_avec_spring_boot.repositories.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;

@SpringBootApplication
public class Tp17ImplementationDUnServiceGRpcAvecSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(Tp17ImplementationDUnServiceGRpcAvecSpringBootApplication.class, args);
    }
    @Bean
    CommandLineRunner start(CompteRepository compteRepository){
        return args -> {
            compteRepository.save(new Compte(UUID.randomUUID().toString(), 2500, "2025-01-01", "EPARGNE"));
            compteRepository.save(new Compte(UUID.randomUUID().toString(), 3000, "2025-01-01", "COURANT"));
            compteRepository.save(new Compte(UUID.randomUUID().toString(), 5000, "2025-01-01", "EPARGNE"));

            compteRepository.findAll().forEach(c -> {
                System.out.println(c.toString());
            });
        };
    }
}
