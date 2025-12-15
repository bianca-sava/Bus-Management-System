package com.example.busmanagementsystem.controller;


import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.exceptions.EntityNotFoundException;
import com.example.busmanagementsystem.model.BusTrip;
import com.example.busmanagementsystem.model.BusTripStatus;
import com.example.busmanagementsystem.model.Ticket;
import com.example.busmanagementsystem.model.DutyAssignment;
import com.example.busmanagementsystem.service.databaseServices.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

@Controller
@RequestMapping("/bus-trip")
public class BusTripController {

    private final BusTripDatabaseService busTripService;
    private final TicketDatabaseService ticketService;
    private final BusDatabaseService busService;
    private final DutyAssignmentsDatabaseService dutyAssignmentService;
    private final RouteDatabaseService routeService;

    @Autowired
    public BusTripController(BusTripDatabaseService busTripService,
                             TicketDatabaseService ticketService,
                             DutyAssignmentsDatabaseService dutyAssignmentService,
                             RouteDatabaseService routeService,
                             BusDatabaseService busService){
        this.busTripService = busTripService;
        this.ticketService = ticketService;
        this.dutyAssignmentService = dutyAssignmentService;
        this.routeService = routeService;
        this.busService = busService;
    }

    @GetMapping
    public String getAllBusTrips(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String routeId,
            @RequestParam(required = false) String busId,
            @RequestParam(required = false) BusTripStatus status,
            @RequestParam(required = false) String minTime,
            @RequestParam(required = false) String maxTime,
            @PageableDefault(size = 10, sort = "startTime", direction = Sort.Direction.DESC)
            Pageable pageable,
            Model model) {

        Page<BusTrip> busTripPage = busTripService.findAllPageable(
                check(id),
                check(routeId),
                check(busId),
                status,
                check(minTime),
                check(maxTime),
                pageable
        );

        model.addAttribute("busTripPage", busTripPage);
        model.addAttribute("busTrips", busTripPage.getContent());
        model.addAttribute("pageable", pageable);

        model.addAttribute("filterId", id);
        model.addAttribute("filterRouteId", routeId);
        model.addAttribute("filterBusId", busId);
        model.addAttribute("filterStatus", status);
        model.addAttribute("filterMinTime", minTime);
        model.addAttribute("filterMaxTime", maxTime);

        model.addAttribute("statusOptions", BusTripStatus.values());

        return "busTrip/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("busTrip", new BusTrip());
        model.addAttribute("isEditMode", false);
        populateDropdowns(model);
        return "busTrip/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        BusTrip existingBusTrip = busTripService.findById(id);

        if (existingBusTrip != null) {
            model.addAttribute("busTrip", existingBusTrip);
            model.addAttribute("isEditMode", true);
            populateDropdowns(model);
            return "busTrip/form";
        }
        return "redirect:/bus-trip";
    }

    @PostMapping("/{id}")
    public String updateBusTrip(@PathVariable String id,
                                @Valid @ModelAttribute("busTrip") BusTrip busTrip,
                                BindingResult bindingResult,
                                Model model) {

        busTrip.setId(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("isEditMode", true);
            populateDropdowns(model);
            return "busTrip/form";
        }
        try {
            busTripService.update(id, busTrip);
        } catch (EntityNotFoundException e) {
            bindingResult.rejectValue("id", "error.busTrip", e.getMessage());
            model.addAttribute("isEditMode", true);
            populateDropdowns(model);
            return "busTrip/form";
        }
        return "redirect:/bus-trip";
    }

    @PostMapping("/create")
    public String createBusTrip(@Valid @ModelAttribute("busTrip") BusTrip busTrip,
                                BindingResult bindingResult,
                                Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("isEditMode", false);
            populateDropdowns(model);
            return "busTrip/form";
        }

        try {
            busTripService.create(busTrip);
        } catch (DuplicateAttributeException e) {
            bindingResult.rejectValue("id", "error.busTrip", e.getMessage());
            model.addAttribute("isEditMode", false);
            populateDropdowns(model);
            return "busTrip/form";
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("isEditMode", false);
            populateDropdowns(model);
            return "busTrip/form";
        }

        return "redirect:/bus-trip";
    }

    @PostMapping("/{id}/delete")
    public String deleteBusTrip(@PathVariable String id) {
        busTripService.delete(id);
        return "redirect:/bus-trip";
    }

    private void populateDropdowns(Model model) {
        model.addAttribute("availableRoutes", routeService.findAll().values());
        model.addAttribute("availableBuses", busService.findAll().values());
        model.addAttribute("statusOptions", BusTripStatus.values());
    }

    @GetMapping("/{id}/tickets")
    public String getTripTickets(@PathVariable String id, Model model) {
        BusTrip trip = busTripService.findById(id);
        if (trip != null) {
            model.addAttribute("tickets", trip.getTickets());
            model.addAttribute("tripId", trip.getId());
            model.addAttribute("tripName", "Trip " + trip.getId());
            return "busTrip/tickets";
        }
        return "redirect:/bus-trip";
    }

    @GetMapping("/{id}/tickets/new")
    public String showAssignTicketForm(@PathVariable String id, Model model) {
        BusTrip trip = busTripService.findById(id);
        if (trip != null) {
            model.addAttribute("tripId", id);
            model.addAttribute("availableTickets", ticketService.findAll().values());
            return "busTrip/assignTicket";
        }
        return "redirect:/bus-trip";
    }

    @PostMapping("/{tripId}/tickets/add")
    public String addTicketToTrip(@PathVariable String tripId, @RequestParam String selectedTicketId) {
        BusTrip trip = busTripService.findById(tripId);
        Ticket ticketToAdd = ticketService.findById(selectedTicketId);

        if (trip != null && ticketToAdd != null) {
            trip.getTickets().add(ticketToAdd);
            busTripService.update(tripId, trip);
        }
        return "redirect:/bus-trip/" + tripId + "/tickets";
    }

    @PostMapping("/{tripId}/tickets/{ticketId}/delete")
    public String deleteTicketFromTrip(@PathVariable String tripId, @PathVariable String ticketId) {
        BusTrip trip = busTripService.findById(tripId);
        if (trip != null) {
            trip.getTickets().removeIf(ticket -> ticket.getId().equals(ticketId));
            busTripService.update(tripId, trip); // Salvează modificarea
        }
        return "redirect:/bus-trip/" + tripId + "/tickets";
    }


    @GetMapping("/{id}/assignments")
    public String getTripAssignments(@PathVariable String id, Model model) {
        BusTrip trip = busTripService.findById(id);
        if (trip != null) {
            model.addAttribute("assignments", trip.getAssignments());
            model.addAttribute("tripId", trip.getId());
            model.addAttribute("tripName", "Trip " + trip.getId());
            return "busTrip/assignments";
        }
        return "redirect:/bus-trip";
    }

    @GetMapping("/{id}/assignments/new")
    public String showAssignAssignmentForm(@PathVariable String id, Model model) {
        BusTrip trip = busTripService.findById(id);
        if (trip != null) {
            model.addAttribute("tripId", id);
            model.addAttribute("availableAssignments", dutyAssignmentService.getAllAssignments().values());
            return "busTrip/assignAssignment";
        }
        return "redirect:/bus-trip";
    }

    @PostMapping("/{tripId}/assignments/add")
    public String addAssignmentToTrip(@PathVariable String tripId, @RequestParam String selectedAssignmentId) {
        BusTrip trip = busTripService.findById(tripId);
        DutyAssignment assignmentToAdd = dutyAssignmentService.getAssignmentById(selectedAssignmentId);

        if (trip != null && assignmentToAdd != null) {
            trip.getAssignments().add(assignmentToAdd);
            busTripService.update(tripId, trip);
        }
        return "redirect:/bus-trip/" + tripId + "/assignments";
    }

    @PostMapping("/{tripId}/assignments/{assignmentId}/delete")
    public String deleteAssignmentFromTrip(@PathVariable String tripId, @PathVariable String assignmentId) {
        BusTrip trip = busTripService.findById(tripId);
        if (trip != null) {
            trip.getAssignments().removeIf(assignment -> assignment.getId().equals(assignmentId));
            busTripService.update(tripId, trip);
        }
        return "redirect:/bus-trip/" + tripId + "/assignments";
    }

    private String check(String s) {
        if (s == null || s.trim().isEmpty()) {
            return null;
        }
        return s.trim();
    }
}