package com.example.ticketflow.service;


import com.example.ticketflow.dao.entities.Ticket;
import com.example.ticketflow.dao.entities.TicketStatus;
import com.example.ticketflow.dao.repositories.TicketRepository;
import com.example.ticketflow.dtos.CreateTicketRequest;
import com.example.ticketflow.dtos.TicketResponse;
import com.example.ticketflow.dtos.UpdateTicketRequest;
import com.example.ticketflow.exceptions.ResourceNotFoundException;
import com.example.ticketflow.mappers.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @Override
    public TicketResponse create(CreateTicketRequest request){
        Ticket ticket = ticketMapper.toEntity(request);
        Ticket saved = ticketRepository.save(ticket);
        return ticketMapper.toResponse(saved);
    }
    @Override
    public List<TicketResponse> getAll(){
        return ticketRepository.findAll()
                .stream()
                .map(ticket -> ticketMapper.toResponse(ticket))
                .toList();
    }
    @Override
    public TicketResponse getById(long id){
        Ticket ticket = ticketRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("ticket", id));
        return ticketMapper.toResponse(ticket);
    }
    @Override
    public TicketResponse update(Long id, UpdateTicketRequest request) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket", id));
        ticketMapper.updateEntity(request, ticket);
        Ticket updated = ticketRepository.save(ticket);
        return ticketMapper.toResponse(updated);
    }
    @Override
    public TicketResponse changeStatus(Long id, String status) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket", id));
        ticket.setStatus(TicketStatus.valueOf(status.toUpperCase()));
        Ticket updated = ticketRepository.save(ticket);
        return ticketMapper.toResponse(updated);
    }
    @Override
    public void delete(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket", id));
        ticketRepository.delete(ticket);
    }

}
