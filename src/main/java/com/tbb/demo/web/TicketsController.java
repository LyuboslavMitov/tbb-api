package com.tbb.demo.web;

import com.tbb.demo.domain.TicketsService;
import com.tbb.demo.domain.UsersService;
import com.tbb.demo.exception.InvalidEntityException;
import com.tbb.demo.model.Ticket;
import com.tbb.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketsController {
    @Autowired
    private TicketsService ticketsService;

    @Autowired
    private UsersService usersService;

    @GetMapping
    public List<Ticket> getTickets(Principal principal) {
        User user = usersService.findByUsername(principal.getName());
        return ticketsService.findAllByUserId(user.getId());
    }

    @GetMapping("{id}") //TODO:Remove principal, only debugging purposes
    public Ticket getTicketById(@PathVariable("id") String ticketId, Principal principal) {
        validateIsOwnerOfTicket(ticketId);
        return ticketsService.findById(ticketId);
    }

    @PostMapping
    public ResponseEntity<Ticket> addTicket(@Valid @RequestBody Ticket ticket, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasFieldErrors()) {
            String message = bindingResult.getFieldErrors().stream()
                    .map(err -> String.format("Invalid '%s' -> '%s': %s\n",
                            err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                    .reduce("", (acc, errStr) -> acc + errStr);
            throw new InvalidEntityException(message);
        }
        ticket.setCompany(principal.getName());
        Ticket created = ticketsService.add(ticket);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").build(created.getId()))
                .body(created);
    }

    @PutMapping("{id}")
    public Ticket update(@PathVariable String id, @Valid @RequestBody Ticket ticket) {
        if (!id.equals(ticket.getId())) {
            throw new InvalidEntityException(
                    String.format("Entity ID='%s' is different from URL resource ID='%s'", ticket.getId(), id));
        }
        validateIsOwnerOfTicket(id);
        return ticketsService.update(ticket);
    }

    @DeleteMapping("{id}")
    public Ticket remove(@PathVariable String id) {
        validateIsOwnerOfTicket(id);
        return ticketsService.remove(id);
    }

    private void validateIsOwnerOfTicket(String ticketId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Ticket ticket = ticketsService.findById(ticketId);
        User currentUser = usersService.findByUsername(authentication.getName());

        if(ticket.getUserId()==null || !ticket.getUserId().equals(currentUser.getId())) {
            throw new InvalidEntityException("You cannot interact with other users tickets");
        }
    }
}
