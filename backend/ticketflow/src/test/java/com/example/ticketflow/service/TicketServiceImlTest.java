package com.example.ticketflow.service;

import com.example.ticketflow.dao.entities.Ticket;
import com.example.ticketflow.dao.entities.TicketPriority;
import com.example.ticketflow.dao.entities.TicketStatus;
import com.example.ticketflow.dao.repositories.TicketRepository;
import com.example.ticketflow.dtos.CreateTicketRequest;
import com.example.ticketflow.dtos.TicketResponse;
import com.example.ticketflow.dtos.UpdateTicketRequest;
import com.example.ticketflow.exceptions.ResourceNotFoundException;
import com.example.ticketflow.mappers.TicketMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit Tests for TicketService Class")
class TicketServiceImlTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TicketMapper ticketMapper;

    @InjectMocks
    private TicketServiceImpl ticketService;

    private Ticket ticket;
    private TicketResponse ticketResponse;
    private CreateTicketRequest createRequest;

    @BeforeEach
    void SetUp() {
        // Preparer un ticket de base
        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setTitle("Bug login");
        ticket.setDescription("Login page crashes");
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setPriority(TicketPriority.HIGH);

        // Preparation de la reponse
        ticketResponse = new TicketResponse(
                1L,
                "Bug login",
                "Login page crashes",
                TicketStatus.OPEN,
                TicketPriority.HIGH,
                null,
                null
        );

        // Préparer la requête de création
        createRequest = new CreateTicketRequest();
        createRequest.setTitle("Bug login");
        createRequest.setDescription("Login page crashes");
        createRequest.setPriority(TicketPriority.HIGH);
    }

    @Test
    @DisplayName("Should create ticket successfully")
    void ShouldCreateTicket() {
        // ARRANGE
        when(ticketMapper.toEntity(createRequest)).thenReturn(ticket);
        when(ticketRepository.save(ticket)).thenReturn(ticket);
        when(ticketMapper.toResponse(ticket)).thenReturn(ticketResponse);

        // ACT
        TicketResponse result = ticketService.create(createRequest);

        // ASSERT
        assertNotNull(result);
        assertEquals("Bug login", result.getTitle());
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    @DisplayName("Should return all tickets")
    void ShouldReturnAllTickets() {
        // ARRANGE
        List<Ticket> tickets = List.of(ticket);
        when(ticketRepository.findAll()).thenReturn(tickets);
        when(ticketMapper.toResponse(ticket)).thenReturn(ticketResponse);

        // ACT
        List<TicketResponse> result = ticketService.getAll();

        // ASSERT
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(ticketRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return ticket when ID exists")
    void ShouldReturnTicketById() {
        // ARRANGE
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketMapper.toResponse(ticket)).thenReturn(ticketResponse);

        // ACT
        TicketResponse result = ticketService.getById(1L);

        // ASSERT
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Bug login", result.getTitle());
        verify(ticketRepository, times(1)).findById(1L);
    }
    @Test
    @DisplayName("Should throw exception when ticket not found")
    void ShouldThrowExceptionWhenTicketNotFound() {
        // ARRANGE
        when(ticketRepository.findById(99L)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> {
            ticketService.getById(99L);
        });

        verify(ticketRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Should update ticket successfully")
    void ShouldUpdateTicket() {
        // ARRANGE
        UpdateTicketRequest updateRequest = new UpdateTicketRequest();
        updateRequest.setTitle("Bug login updated");
        updateRequest.setDescription("Login page crashes on mobile");
        updateRequest.setPriority(TicketPriority.CRITICAL);

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(ticket)).thenReturn(ticket);
        when(ticketMapper.toResponse(ticket)).thenReturn(ticketResponse);

        // ACT
        TicketResponse result = ticketService.update(1L, updateRequest);

        // ASSERT
        assertNotNull(result);
        verify(ticketRepository, times(1)).findById(1L);
        verify(ticketMapper, times(1)).updateEntity(updateRequest, ticket);
        verify(ticketRepository, times(1)).save(ticket);

    }
    @Test
    @DisplayName("Should throw exception when updating non existent ticket")
    void ShouldThrowExceptionWhenUpdatingNonExistentTicket() {
        // ARRANGE
        UpdateTicketRequest updateRequest = new UpdateTicketRequest();
        updateRequest.setTitle("Bug login updated");
        updateRequest.setDescription("Login page crashes on mobile");
        updateRequest.setPriority(TicketPriority.CRITICAL);
        when(ticketRepository.findById(99L)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> {
            ticketService.update(99L, updateRequest);
        });

        verify(ticketRepository, times(1)).findById(99L);
        verify(ticketRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should change ticket status successfully")
    void ShouldChangeStatus() {
        // ARRANGE
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(ticket)).thenReturn(ticket);
        when(ticketMapper.toResponse(ticket)).thenReturn(ticketResponse);

        // ACT
        TicketResponse result = ticketService.changeStatus(1L, "RESOLVED");

        // ASSERT
        assertNotNull(result);
        assertEquals(TicketStatus.RESOLVED, ticket.getStatus());
        verify(ticketRepository, times(1)).findById(1L);
        verify(ticketRepository, times(1)).save(ticket);

    }
    @Test
    @DisplayName("Should throw exception when changing status of non existent ticket")
    void shouldThrowExceptionWhenChangingStatusOfNonExistentTicket(){
        // ARRANGE
        when(ticketRepository.findById(99L)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> {
            ticketService.changeStatus(99L, "RESOLVED");
        });

        verify(ticketRepository, times(1)).findById(99L);
        verify(ticketRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete ticket successfully")
    void ShouldDeleteTicket() {
        // ARRANGE
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        // ACT
        ticketService.delete(1L);

        // ASSERT
        verify(ticketRepository, times(1)).findById(1L);
        verify(ticketRepository, times(1)).delete(ticket);
    }
    @Test
    @DisplayName("Should throw exception when deleting non existent ticket")
    void shouldThrowExceptionWhenDeletingNonExistentTicket() {
        // ARRANGE
        when(ticketRepository.findById(99L)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> {
            ticketService.delete(99L);
        });

        verify(ticketRepository, times(1)).findById(99L);
        verify(ticketRepository, never()).delete(any());
    }
}