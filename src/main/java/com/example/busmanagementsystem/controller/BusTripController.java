package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.model.BusTrip;
import com.example.busmanagementsystem.model.BusTripStatus;
import com.example.busmanagementsystem.model.Ticket;
import com.example.busmanagementsystem.model.DutyAssignment;
import com.example.busmanagementsystem.service.databaseServices.*;
import com.example.busmanagementsystem.service.inFileServices.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bus-trip")
public class BusTripController {

//    private final BusTripService busTripService;
//    private final TicketService ticketService;
//    private final DutyAssignmentsService dutyAssignmentService;
//    private final RouteService routeService;
//    private final BusService busService;
    private final BusTripDatabaseService busTripService;
    private final TicketDatabaseService ticketService;
    private final BusDatabaseService busService;
    private final DutyAssignmentsDatabaseService dutyAssignmentService;
    private final RouteDatabaseService routeService;

    @Autowired
    public BusTripController(BusTripDatabaseService busTripService,
                             TicketDatabaseService ticketService,
                             DutyAssignmentsDatabaseService dutyAssignmentService,
                             RouteDatabaseService routeService, BusDatabaseService busService){
        this.busTripService = busTripService;
        this.ticketService = ticketService;
        this.dutyAssignmentService = dutyAssignmentService;
        this.routeService = routeService;
        this.busService = busService;
    }

//    @Autowired
//    public BusTripController(BusTripService busTripService,
//                             TicketService ticketService,
//                             DutyAssignmentsService dutyAssignmentService,
//                             RouteService routeService, BusService busService) {
//        this.busTripService = busTripService;
//        this.ticketService = ticketService;
//        this.dutyAssignmentService = dutyAssignmentService;
//        this.routeService = routeService;
//        this.busService = busService;
//    }



    @GetMapping
    public String getAllBusTrips(Model model) {
        model.addAttribute("busTrips", busTripService.findAll().values());
        return "busTrip/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("busTrip", new BusTrip());
        model.addAttribute("availableRoutes", routeService.findAll().values());
        model.addAttribute("availableBuses", busService.findAll().values());
        return "busTrip/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        BusTrip existingBusTrip = busTripService.findById(id);

        if (existingBusTrip != null) {
            model.addAttribute("busTrip", existingBusTrip);
            model.addAttribute("statusOptions", BusTripStatus.values());
            model.addAttribute("availableRoutes", routeService.findAll().values());
            model.addAttribute("availableBuses", busService.findAll().values());
            return "busTrip/form";
        }

        return "redirect:/bus-trip";
    }

    @PostMapping("/{id}")
    public String updateBusTrip(@PathVariable String id,
                                @RequestParam String routeId,
                                @RequestParam String busId,
                                @RequestParam String startTime,
                                @RequestParam BusTripStatus status) {

        BusTrip existingBusTrip = busTripService.findById(id);
        if (existingBusTrip != null) {
            existingBusTrip.setRouteId(routeId);
            existingBusTrip.setBusId(busId);
            existingBusTrip.setStartTime(startTime);
            existingBusTrip.setStatus(status);

            busTripService.update(id, existingBusTrip);
        }

        return "redirect:/bus-trip";
    }

    @PostMapping("/create")
    public String createBusTrip(@RequestParam String id, @RequestParam String routeId, @RequestParam String busId, @RequestParam String startTime) {
        busTripService.create(new BusTrip(id, routeId, busId, startTime));
        return "redirect:/bus-trip";
    }

    @PostMapping("/{id}/delete")
    public String deleteBusTrip(@PathVariable String id) {
        busTripService.delete(id);
        return "redirect:/bus-trip";
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
            return "busTrip/assignments"; // Pagină HTML nouă
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
}