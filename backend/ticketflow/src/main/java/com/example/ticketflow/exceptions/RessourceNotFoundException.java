package com.example.ticketflow.exceptions;

public class RessourceNotFoundException extends RuntimeException{
    public RessourceNotFoundException(String ressourceName , Long id){
        super(ressourceName + "avec l'id " + id + "est introuvable");
    }
}
