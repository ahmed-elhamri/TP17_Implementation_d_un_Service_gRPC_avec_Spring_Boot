package com.example.tp17_implementation_d_un_service_grpc_avec_spring_boot.controllers;

import com.example.tp17_implementation_d_un_service_grpc_avec_spring_boot.services.CompteService;
import io.grpc.stub.StreamObserver;
import ma.projet.grpc.stubs.*;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.UUID;
import java.util.stream.Collectors;

@GrpcService
public class CompteServiceImpl extends CompteServiceGrpc.CompteServiceImplBase {

    private final CompteService compteService;

    public CompteServiceImpl(CompteService compteService) {
        this.compteService = compteService;
    }

    @Override
    public void allComptes(GetAllComptesRequest request, StreamObserver<GetAllComptesResponse> responseObserver) {

        var comptes = compteService.findAllComptes().stream()
                .map(c -> Compte.newBuilder()
                        .setId(c.getId())
                        .setSolde(c.getSolde())
                        .setDateCreation(c.getDateCreation())
                        .setType(TypeCompte.valueOf(c.getType()))
                        .build())
                .collect(Collectors.toList());

        responseObserver.onNext(
                GetAllComptesResponse.newBuilder()
                        .addAllComptes(comptes)
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void compteById(GetCompteByIdRequest request, StreamObserver<GetCompteByIdResponse> responseObserver) {
        var compte = compteService.findCompteById(request.getId());

        if (compte == null) {
            responseObserver.onError(new Throwable("Compte non trouvé: " + request.getId()));
            return;
        }

        var grpcCompte = Compte.newBuilder()
                .setId(compte.getId())
                .setSolde(compte.getSolde())
                .setDateCreation(compte.getDateCreation())
                .setType(TypeCompte.valueOf(compte.getType()))
                .build();

        responseObserver.onNext(
                GetCompteByIdResponse.newBuilder()
                        .setCompte(grpcCompte)
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void totalSolde(GetTotalSoldeRequest request, StreamObserver<GetTotalSoldeResponse> responseObserver) {

        var comptes = compteService.findAllComptes();

        int count = comptes.size();
        float sum = (float) comptes.stream().mapToDouble(c -> c.getSolde()).sum();
        float average = count > 0 ? sum / count : 0;

        var stats = SoldeStats.newBuilder()
                .setCount(count)
                .setSum(sum)
                .setAverage(average)
                .build();

        responseObserver.onNext(
                GetTotalSoldeResponse.newBuilder()
                        .setStats(stats)
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void saveCompte(SaveCompteRequest request, StreamObserver<SaveCompteResponse> responseObserver) {

        var compteReq = request.getCompte();

        var compte = new com.example.tp17_implementation_d_un_service_grpc_avec_spring_boot.entities.Compte();
        compte.setId(UUID.randomUUID().toString());
        compte.setSolde(compteReq.getSolde());
        compte.setDateCreation(compteReq.getDateCreation());
        compte.setType(compteReq.getType().name());

        var savedCompte = compteService.saveCompte(compte);

        var grpcCompte = Compte.newBuilder()
                .setId(savedCompte.getId())
                .setSolde(savedCompte.getSolde())
                .setDateCreation(savedCompte.getDateCreation())
                .setType(TypeCompte.valueOf(savedCompte.getType()))
                .build();

        responseObserver.onNext(
                SaveCompteResponse.newBuilder()
                        .setCompte(grpcCompte)
                        .build()
        );
        responseObserver.onCompleted();
    }
}
