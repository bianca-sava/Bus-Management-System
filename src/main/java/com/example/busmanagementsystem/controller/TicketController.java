package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.model.Ticket;
import com.example.busmanagementsystem.service.BusTripService; // <-- NOU
import com.example.busmanagementsystem.service.PassengerService; // <-- NOU
import com.example.busmanagementsystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;
    private final BusTripService busTripService;
    private final PassengerService passengerService;

    @Autowired
    public TicketController(TicketService ticketService,
                            BusTripService busTripService,
                            PassengerService passengerService) {
        this.ticketService = ticketService;
        this.busTripService = busTripService;
        this.passengerService = passengerService;
    }

    @GetMapping
    public String getAllTickets(Model model) {
        model.addAttribute("tickets", ticketService.findAll().values());
        return "ticket/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("ticket", new Ticket());


        model.addAttribute("allTrips", busTripService.findAll().values());
        model.addAttribute("allPassengers", passengerService.findAll().values());

        return "ticket/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Ticket existingTicket = ticketService.findById(id);

        if (existingTicket != null) {
            model.addAttribute("ticket", existingTicket);

            // Avem nevoie de liste și aici
            model.addAttribute("allTrips", busTripService.findAll().values());
            model.addAttribute("allPassengers", passengerService.findAll().values());

            return "ticket/form";
        }
        return "redirect:/ticket";
    }

    @PostMapping("/{id}")
    public String updateTicket(@PathVariable String id,
                               @RequestParam String tripId,
                               @RequestParam String passengerId,
                               @RequestParam String seatNumber,
                               @RequestParam double price,
                               @RequestParam(name = "checkedIn", required = false) Boolean checkedIn) {

        Ticket existingTicket = ticketService.findById(id);

        if (existingTicket != null) {

            existingTicket.setTripId(tripId);
            existingTicket.setPassengerId(passengerId);
            existingTicket.setSeatNumber(seatNumber);
            existingTicket.setPrice(price);
            existingTicket.setCheckedIn(checkedIn != null && checkedIn);

            ticketService.update(id, existingTicket);
        }
        return "redirect:/ticket";
    }

    @PostMapping("/create")
    public String createTicket(@RequestParam String id,
                               @RequestParam String tripId,
                               @RequestParam String passengerId,
                               @RequestParam String seatNumber,
                               @RequestParam double price) {

        Ticket newTicket = new Ticket(id, tripId, passengerId, seatNumber, price);

        ticketService.create(newTicket);

        return "redirect:/ticket";
    }

    @PostMapping("/{id}/delete")
    public String deleteTicket(@PathVariable String id) {
        ticketService.delete(id);
        return "redirect:/ticket";
    }
}