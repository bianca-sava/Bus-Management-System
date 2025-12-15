package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.exceptions.EntityNotFoundException;
import com.example.busmanagementsystem.model.Ticket;
import com.example.busmanagementsystem.service.databaseServices.BusTripDatabaseService;
import com.example.busmanagementsystem.service.databaseServices.PassengerDatabaseService;
import com.example.busmanagementsystem.service.databaseServices.TicketDatabaseService;
import com.example.busmanagementsystem.service.inFileServices.BusTripService;
import com.example.busmanagementsystem.service.inFileServices.PassengerService;
import com.example.busmanagementsystem.service.inFileServices.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ticket")
public class TicketController {

//    private final TicketService ticketService;
//    private final BusTripService busTripService;
//    private final PassengerService passengerService;
    private final Validator validator;
    private final TicketDatabaseService ticketService;
    private final BusTripDatabaseService busTripService;
    private final PassengerDatabaseService passengerService;

    @Autowired
    public TicketController(TicketDatabaseService ticketService,
                            BusTripDatabaseService busTripService,
                            PassengerDatabaseService passengerService,
                            Validator validator) {
        this.ticketService = ticketService;
        this.busTripService = busTripService;
        this.passengerService = passengerService;
        this.validator = validator;
    }

//    @Autowired
//    public TicketController(TicketService ticketService,
//                            BusTripService busTripService,
//                            PassengerService passengerService,
//                            Validator validator) {
//        this.ticketService = ticketService;
//        this.busTripService = busTripService;
//        this.passengerService = passengerService;
//        this.validator = validator;
//    }

    @GetMapping
    public String getAllTickets(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable,
            Model model) {

        Page<Ticket> ticketPage = ticketService.findAllPageable(pageable);

        model.addAttribute("ticketPage", ticketPage);
        model.addAttribute("tickets", ticketPage.getContent());
        model.addAttribute("pageable", pageable);

        return "ticket/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("isEditMode", false);

        model.addAttribute("allTrips", busTripService.findAll().values());
        model.addAttribute("allPassengers", passengerService.findAll().values());

        return "ticket/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Ticket existingTicket = ticketService.findById(id);

        if (existingTicket != null) {
            model.addAttribute("ticket", existingTicket);
            model.addAttribute("isEditMode", true);

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
                               @RequestParam(name = "checkedIn", required = false) Boolean checkedIn,
                               Model model) {

        Ticket existingTicket = ticketService.findById(id);

        if (existingTicket != null) {

            existingTicket.setTripId(tripId);
            existingTicket.setPassengerId(passengerId);
            existingTicket.setSeatNumber(seatNumber);
            existingTicket.setPrice(price);
            existingTicket.setCheckedIn(checkedIn != null && checkedIn);

            DataBinder binder = new DataBinder(existingTicket, "ticket");
            binder.setValidator(validator);
            binder.validate();
            BindingResult bindingResult = binder.getBindingResult();

            if (bindingResult.hasErrors()) {
                model.addAttribute("org.springframework.validation.BindingResult.ticket", bindingResult);

                model.addAttribute("ticket", existingTicket);
                model.addAttribute("isEditMode", true);
                model.addAttribute("allTrips", busTripService.findAll().values());
                model.addAttribute("allPassengers", passengerService.findAll().values());
                return "ticket/form";
            }

            try {
                ticketService.update(id, existingTicket);
            }
            catch (EntityNotFoundException | DuplicateAttributeException e) {
                if (e instanceof EntityNotFoundException) {
                    model.addAttribute("errorField", ((EntityNotFoundException) e).getFieldName());
                }
                model.addAttribute("errorMessage", e.getMessage());
                model.addAttribute("ticket", existingTicket);
                model.addAttribute("isEditMode", true);
                model.addAttribute("allTrips", busTripService.findAll().values());
                model.addAttribute("allPassengers", passengerService.findAll().values());

                return "ticket/form";
            }
        }
        return "redirect:/ticket";
    }

    @PostMapping("/create")
    public String createTicket(@RequestParam String id,
                               @RequestParam String tripId,
                               @RequestParam String passengerId,
                               @RequestParam String seatNumber,
                               @RequestParam double price,
                               Model model) {

        Ticket newTicket = new Ticket(id, tripId, passengerId, seatNumber, price);

        DataBinder binder = new DataBinder(newTicket, "ticket");
        binder.setValidator(validator);
        binder.validate();
        BindingResult bindingResult = binder.getBindingResult();

        if (bindingResult.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.ticket", bindingResult);

            model.addAttribute("ticket", newTicket);
            model.addAttribute("isEditMode", false);
            model.addAttribute("allTrips", busTripService.findAll().values());
            model.addAttribute("allPassengers", passengerService.findAll().values());
            return "ticket/form";
        }

        try {
            ticketService.create(newTicket);
        }
        catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("errorField", e.getFieldName());
            model.addAttribute("ticket", newTicket);
            model.addAttribute("isEditMode", false);
            model.addAttribute("allTrips", busTripService.findAll().values());
            model.addAttribute("allPassengers", passengerService.findAll().values());
            return "ticket/form";
        }
        catch (DuplicateAttributeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("errorField", e.getAttributeName());

            model.addAttribute("ticket", newTicket);
            model.addAttribute("isEditMode", false);
            model.addAttribute("allTrips", busTripService.findAll().values());
            model.addAttribute("allPassengers", passengerService.findAll().values());
            return "ticket/form";
        }

        return "redirect:/ticket";
    }

    @PostMapping("/{id}/delete")
    public String deleteTicket(@PathVariable String id) {
        ticketService.delete(id);
        return "redirect:/ticket";
    }
}