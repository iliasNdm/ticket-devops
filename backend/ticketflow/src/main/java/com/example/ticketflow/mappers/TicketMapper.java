package com.example.ticketflow.mappers;


import com.example.ticketflow.dao.entities.Ticket;
import com.example.ticketflow.dao.entities.TicketStatus;
import com.example.ticketflow.dtos.CreateTicketRequest;
import com.example.ticketflow.dtos.TicketResponse;
import com.example.ticketflow.dtos.UpdateTicketRequest;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    public Ticket toEntity(CreateTicketRequest request) {
        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setStatus(TicketStatus.OPEN);
        return ticket;
    }
    public TicketResponse toResponse(Ticket ticket) {
        return new TicketResponse(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt()
        );
    }
    public void updateEntity(UpdateTicketRequest request, Ticket ticket) {
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());

        if (request.getStatus() != null) {
            ticket.setStatus(request.getStatus());
        }
    }
}
