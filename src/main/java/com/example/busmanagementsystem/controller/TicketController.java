package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.model.Ticket;
import com.example.busmanagementsystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public String getAllTickets(Model model) {
        model.addAttribute("ticket", ticketService.findAll().values());

        return "ticket/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("ticket", new Ticket());

        return "ticket/form";
    }

    @PostMapping
    public String createTicket(@RequestParam String id,
                               @RequestParam String tripId,
                               @RequestParam String passengerId,
                               @RequestParam String seatNumber,
                               @RequestParam double price) {
        Ticket newTicket = new Ticket(id,  tripId, passengerId, seatNumber, price);

        ticketService.create(newTicket);

        return "redirect:/ticket";
    }

    @PostMapping("/{id}/delete")
    public String deleteTicket(@PathVariable String id) {
        ticketService.delete(id);

        return "redirect:/ticket";
    }

}
