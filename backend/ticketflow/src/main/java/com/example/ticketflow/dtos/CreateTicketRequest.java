package com.example.ticketflow.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTicketRequest {
    private String title;
    private String description;
}
