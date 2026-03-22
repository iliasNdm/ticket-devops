package com.example.ticketflow.service;

import com.example.ticketflow.dao.entities.TicketStatus;
import com.example.ticketflow.dtos.CreateTicketRequest;
import com.example.ticketflow.dtos.TicketResponse;
import com.example.ticketflow.dtos.UpdateTicketRequest;

import java.util.List;

public interface TicketService {
    TicketResponse create(CreateTicketRequest request);
    List<TicketResponse> getAll();
    TicketResponse getById(long id);
    TicketResponse update(Long id, UpdateTicketRequest status);
    TicketResponse changeStatus(Long id , String staus);
    void delete(Long id);

}
