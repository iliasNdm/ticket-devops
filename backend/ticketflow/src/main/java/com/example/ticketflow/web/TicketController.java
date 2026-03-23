package com.example.ticketflow.web;

import com.example.ticketflow.dtos.CreateTicketRequest;
import com.example.ticketflow.dtos.TicketResponse;
import com.example.ticketflow.dtos.UpdateTicketRequest;
import com.example.ticketflow.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponse> create(@RequestBody CreateTicketRequest request) {
        TicketResponse response = ticketService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/tickets — list all tickets
    @GetMapping
    public ResponseEntity<List<TicketResponse>> getAll() {
        List<TicketResponse> tickets = ticketService.getAll();
        return ResponseEntity.ok(tickets);
    }

    // GET /api/tickets/{id} — ticket details
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getById(@PathVariable Long id) {
        TicketResponse ticket = ticketService.getById(id);
        return ResponseEntity.ok(ticket);
    }

    // PUT /api/tickets/{id} — modify ticket
    @PutMapping("/{id}")
    public ResponseEntity<TicketResponse> update(@PathVariable Long id,
                                                 @RequestBody UpdateTicketRequest request) {
        TicketResponse updated = ticketService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    // PATCH /api/tickets/{id}/status — changing ticket's status
    @PatchMapping("/{id}/status")
    public ResponseEntity<TicketResponse> changeStatus(@PathVariable Long id,
                                                       @RequestBody String status) {
        TicketResponse updated = ticketService.changeStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/tickets/{id} — delete ticket
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
