package com.example.tp17_implementation_d_un_service_grpc_avec_spring_boot.services;

import com.example.tp17_implementation_d_un_service_grpc_avec_spring_boot.entities.Compte;
import com.example.tp17_implementation_d_un_service_grpc_avec_spring_boot.repositories.CompteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompteService {
    private final CompteRepository compteRepository;

    public CompteService(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    public List<Compte> findAllComptes() {
        return compteRepository.findAll();
    }

    public Compte findCompteById(String id) {
        return compteRepository.findById(id).orElse(null);
    }

    public Compte saveCompte(Compte compte) {
        return compteRepository.save(compte);
    }
}