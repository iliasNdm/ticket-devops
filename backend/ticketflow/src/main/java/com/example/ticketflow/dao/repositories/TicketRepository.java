package com.example.ticketflow.dao.repositories;

import com.example.ticketflow.dao.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long > {

}
