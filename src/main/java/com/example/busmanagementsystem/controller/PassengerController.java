package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.model.Passenger;
import com.example.busmanagementsystem.model.Ticket;
import com.example.busmanagementsystem.service.PassengerService;
import com.example.busmanagementsystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/passenger")
public class PassengerController {
    private final PassengerService passengerService;
    private final TicketService ticketService;

    @Autowired
    public PassengerController(PassengerService passengerService,
                               TicketService ticketService) {
        this.passengerService = passengerService;
        this.ticketService = ticketService;
    }

    @GetMapping
    public String getAllPassengers(Model model) {
        model.addAttribute("passengers", passengerService.findAll().values());
        return "passenger/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("passenger", new Passenger());
        return "passenger/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Passenger existingPassenger = passengerService.findById(id);
        if (existingPassenger != null) {
            model.addAttribute("passenger", existingPassenger);
            return "passenger/form";
        }
        return "redirect:/passenger";
    }

    @PostMapping("/{id}")
    public String updatePassenger(@PathVariable String id,
                                  @RequestParam String name,
                                  @RequestParam String currency) {
        Passenger existingPassenger = passengerService.findById(id);
        if (existingPassenger != null) {
            existingPassenger.setName(name);
            existingPassenger.setCurrency(currency);
            passengerService.update(id, existingPassenger);
        }
        return "redirect:/passenger";
    }

    @PostMapping("/create")
    public String createPassenger(@RequestParam String id,
                                  @RequestParam String name,
                                  @RequestParam String currency) {
        Passenger newPassenger = new Passenger(id, name, currency);
        passengerService.create(newPassenger);
        return "redirect:/passenger";
    }

    @PostMapping("/{id}/delete")
    public String deletePassenger(@PathVariable String id) {
        passengerService.delete(id);
        return "redirect:/passenger";
    }

    @GetMapping("/{id}/tickets")
    public String getPassengerTickets(@PathVariable String id, Model model) {
        Passenger passenger = passengerService.findById(id);
        if (passenger != null) {
            model.addAttribute("tickets", passenger.getTickets());
            model.addAttribute("passengerId", passenger.getId());
            model.addAttribute("passengerName", passenger.getName());
            return "passenger/tickets";
        }
        return "redirect:/passenger";
    }

    @GetMapping("/{id}/tickets/new")
    public String showAssignTicketForm(@PathVariable String id, Model model) {
        Passenger passenger = passengerService.findById(id);
        if (passenger != null) {
            model.addAttribute("passengerId", id);
            model.addAttribute("availableTickets", ticketService.findAll().values());
            return "passenger/assignTicket";
        }
        return "redirect:/passenger";
    }

    @PostMapping("/{passengerId}/tickets/add")
    public String addTicketToPassenger(@PathVariable String passengerId, @RequestParam String selectedTicketId) {
        Passenger passenger = passengerService.findById(passengerId);
        Ticket ticketToAdd = ticketService.findById(selectedTicketId);

        if (passenger != null && ticketToAdd != null) {
            passenger.getTickets().add(ticketToAdd);
            passengerService.update(passengerId, passenger);
        }
        return "redirect:/passenger/" + passengerId + "/tickets";
    }

    @PostMapping("/{passengerId}/tickets/{ticketId}/delete")
    public String deleteTicketFromPassenger(@PathVariable String passengerId, @PathVariable String ticketId) {
        Passenger passenger = passengerService.findById(passengerId);
        if (passenger != null) {
            passenger.getTickets().removeIf(ticket -> ticket.getId().equals(ticketId));
            passengerService.update(passengerId, passenger);
        }
        return "redirect:/passenger/" + passengerId + "/tickets";
    }
}