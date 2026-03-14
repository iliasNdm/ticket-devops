package com.example.ticketflow.dtos;

import com.example.ticketflow.dao.entities.TicketStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTicketRequest {
        private String title;
        private String description;
        private TicketStatus status;
    }

