package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.model.Passenger;
import com.example.busmanagementsystem.model.Ticket;
import com.example.busmanagementsystem.service.databaseServices.PassengerDatabaseService;
import com.example.busmanagementsystem.service.databaseServices.TicketDatabaseService;
import com.example.busmanagementsystem.service.inFileServices.PassengerService;
import com.example.busmanagementsystem.service.inFileServices.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/passenger")
public class PassengerController {
//    private final PassengerService passengerService;
//    private final TicketService ticketService;
    private final PassengerDatabaseService passengerService;
    private final TicketDatabaseService ticketService;
    private final Validator validator;

    @Autowired
    public PassengerController(PassengerDatabaseService passengerService,
                               TicketDatabaseService ticketService,
                               Validator validator) {
        this.passengerService = passengerService;
        this.ticketService = ticketService;
        this.validator = validator;
    }

//    @Autowired
//    public PassengerController(PassengerService passengerService,
//                               TicketService ticketService,
//                               Validator validator) {
//        this.passengerService = passengerService;
//        this.ticketService = ticketService;
//        this.validator = validator;
//    }

    @GetMapping
    public String getAllPassengers(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String currency,
            @RequestParam(required = false) Integer minTickets,
            @RequestParam(required = false) Integer maxTickets,
            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable,
            Model model) {

        Page<Passenger> passengerPage = passengerService.findPassengersByCriteria(
                id, name, currency, minTickets, maxTickets, pageable
        );

        model.addAttribute("passengerPage", passengerPage);
        model.addAttribute("passengers", passengerPage.getContent());
        model.addAttribute("pageable", pageable);
        model.addAttribute("filterId", id);
        model.addAttribute("filterName", name);
        model.addAttribute("filterCurrency", currency);
        model.addAttribute("filterMinTickets", minTickets);
        model.addAttribute("filterMaxTickets", maxTickets);

        return "passenger/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("passenger", new Passenger());
        model.addAttribute("isEditMode", false);
        return "passenger/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Passenger existingPassenger = passengerService.findById(id);
        if (existingPassenger != null) {
            model.addAttribute("passenger", existingPassenger);
            model.addAttribute("isEditMode", true);

            return "passenger/form";
        }
        return "redirect:/passenger";
    }

    @PostMapping("/{id}")
    public String updatePassenger(@PathVariable String id,
                                  @RequestParam String name,
                                  @RequestParam String currency,
                                  Model model) {

        Passenger existingPassenger = passengerService.findById(id);

        if (existingPassenger != null) {

            existingPassenger.setName(name);
            existingPassenger.setCurrency(currency);

            DataBinder binder = new DataBinder(existingPassenger, "passenger");
            binder.setValidator(validator);
            binder.validate();
            BindingResult bindingResult = binder.getBindingResult();

            if(bindingResult.hasErrors()) {
                model.addAttribute("org.springframework.validation.BindingResult.passenger", bindingResult);
                model.addAttribute("passenger", existingPassenger);
                model.addAttribute("isEditMode", true);
                return  "passenger/form";
            }

            try {
                passengerService.update(id, existingPassenger);
            }
            catch (DuplicateAttributeException e){
                model.addAttribute("errorMessage", e.getMessage());
                model.addAttribute("errorField", e.getAttributeName());
                model.addAttribute("passenger", existingPassenger);
                model.addAttribute("isEditMode", true);
            }
        }
        return "redirect:/passenger";
    }

    @PostMapping("/create")
    public String createPassenger(@RequestParam String id,
                                  @RequestParam String name,
                                  @RequestParam String currency,
                                  Model model) {
        Passenger newPassenger = new Passenger(id, name, currency);

        DataBinder binder = new DataBinder(newPassenger, "passenger");
        binder.setValidator(validator);
        binder.validate();
        BindingResult bindingResult = binder.getBindingResult();

        if(bindingResult.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.passenger", bindingResult);
            model.addAttribute("passenger", newPassenger);
            model.addAttribute("isEditMode", false);
            return  "passenger/form";
        }

        try {
            passengerService.create(newPassenger);
        }
        catch (DuplicateAttributeException e){
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("errorField", e.getAttributeName());
            model.addAttribute("passenger", newPassenger);
            model.addAttribute("isEditMode", false);
        }
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
            ticketToAdd.setPassengerId(passengerId);

            ticketService.update(selectedTicketId, ticketToAdd);
            passenger.getTickets().add(ticketToAdd);
            passengerService.update(passengerId, passenger);
        }
        return "redirect:/passenger/" + passengerId + "/tickets";
    }

    @PostMapping("/{passengerId}/tickets/{ticketId}/delete")
    public String deleteTicketFromPassenger(@PathVariable String passengerId, @PathVariable String ticketId) {
        ticketService.delete(ticketId);
        return "redirect:/passenger/" + passengerId + "/tickets";
    }
}