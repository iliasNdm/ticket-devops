package com.example.ticketflow.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String ressourceName , Long id){
        super(ressourceName + "avec l'id " + id + "est introuvable");
    }
}
