package com.example.ticketflow.dtos;


import com.example.ticketflow.dao.entities.TicketPriority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTicketRequest {
    private String title;
    private String description;
    private TicketPriority priority;
}
