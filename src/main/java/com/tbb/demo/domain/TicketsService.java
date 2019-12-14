package com.tbb.demo.domain;


import com.tbb.demo.dao.TicketsRepository;
import com.tbb.demo.exception.NonexisitngEntityException;
import com.tbb.demo.model.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TicketsService {
    @Autowired
    private TicketsRepository repo;

    public List<Ticket> findAll() {
        return repo.findAll();
    }

    public Ticket findById(Long ticketId) {
        return repo.findById(ticketId).orElseThrow(() -> new NonexisitngEntityException(
                String.format("Ticket with ID='%s' does not exist.", ticketId)));
    }

    public Ticket add(Ticket ticket) {
        return repo.insert(ticket);
    }

    public Ticket update(Ticket ticket) {
        Optional<Ticket> old = repo.findById(ticket.getId());
        if (!old.isPresent()) {
            throw new NonexisitngEntityException(
                    String.format("Ticket with ID='%s' does not exist.", ticket.getId()));
        }
        return repo.save(ticket);
    }

    public Ticket remove(Long ticketId) {
        Optional<Ticket> old = repo.findById(ticketId);
        log.info("!!!!!! TicketID = " + ticketId);
        if (!old.isPresent()) {
            throw new NonexisitngEntityException(
                    String.format("Ticket with ID='%s' does not exist.", ticketId));
        }
        repo.deleteById(ticketId);
        return old.get();
    }
}
