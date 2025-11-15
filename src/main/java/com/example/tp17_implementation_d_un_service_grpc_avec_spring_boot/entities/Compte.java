package com.example.tp17_implementation_d_un_service_grpc_avec_spring_boot.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compte {
    // Getters et Setters
    @Id
    private String id;
    private float solde;
    private String dateCreation;
    private String type;

}