package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.model.DutyAssignment;
import com.example.busmanagementsystem.model.TripManager;
import com.example.busmanagementsystem.service.DutyAssignmentsService;
import com.example.busmanagementsystem.service.TripManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tripmanager")
public class TripManagerController {
    private final TripManagerService tripManagerService;
    public final DutyAssignmentsService dutyAssignmentsService;

    @Autowired
    public TripManagerController(TripManagerService service, DutyAssignmentsService dutyAssignmentsService) {
        this.tripManagerService = service;
        this.dutyAssignmentsService = dutyAssignmentsService;
    }

    @GetMapping
    public String getAllTripManagers(Model model) {
        model.addAttribute("tripmanagers", tripManagerService.findAllTripManagers().values());
        return "tripmanager/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("tripmanager", new TripManager());
        return "tripmanager/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        TripManager existingManager = tripManagerService.getTripManagerById(id);

        if (existingManager != null) {
            model.addAttribute("tripmanager", existingManager);
            return "tripmanager/form";
        }
        return "redirect:/tripmanager";
    }

    @PostMapping("/{id}")
    public String updateTripManager(@PathVariable String id,
                                    @RequestParam String name,
                                    @RequestParam String employeeCode) {

        TripManager existingManager = tripManagerService.getTripManagerById(id);

        if (existingManager != null) {
            existingManager.setName(name);
            existingManager.setEmployeeCode(employeeCode);

            tripManagerService.updateTripManager(id, existingManager);
        }
        return "redirect:/tripmanager";
    }

    @PostMapping("/create")
    public String createTripManager(@RequestParam String id,
                                    @RequestParam String name,
                                    @RequestParam String employeeCode) {

        TripManager newTripManager = new TripManager(id, name, employeeCode);

        tripManagerService.addTripManager(newTripManager);

        return "redirect:/tripmanager";
    }

    @PostMapping("/{id}/delete")
    public String deleteTripManager(@PathVariable String id) {
        tripManagerService.deleteTripManager(id);
        return "redirect:/tripmanager";
    }

    @GetMapping("/{id}/assignments")
    public String getDriverAssignments(@PathVariable String id, Model model) {
        TripManager tripManager = tripManagerService.getTripManagerById(id);

        if(tripManager != null) {
            model.addAttribute("assignments", tripManager.getAssignments());
            model.addAttribute("tripManagerId", tripManager.getId());
            model.addAttribute("tripManagerName", tripManager.getName());

            return "tripmanager/assignments";
        }

        return "redirect:/tripmanager";
    }

    @GetMapping("/{id}/assignments/new")
    public String showAssignAssignmentForm(@PathVariable String id, Model model) {
         TripManager tripManager = tripManagerService.getTripManagerById(id);

        if(tripManager != null) {
            model.addAttribute("tripManagerId", id);
            model.addAttribute("availableAssignments", dutyAssignmentsService.getAllAssignments().values());

            return "tripmanager/assignAssignment";
        }

        return "redirect:/tripmanager";
    }

    @PostMapping("/{tripmanagerId}/assignments/add")
    public String addAssignmentToDriver(@PathVariable String tripmanagerId, @RequestParam String selectedAssignmentId) {
        TripManager tripManager = tripManagerService.getTripManagerById(tripmanagerId);
        DutyAssignment assignmentToAdd = dutyAssignmentsService.getAssignmentById(selectedAssignmentId);

        if(tripManager != null && assignmentToAdd != null) {
            tripManager.getAssignments().add(assignmentToAdd);

            tripManagerService.updateTripManager(tripmanagerId, tripManager);
        }

        return "redirect:/tripmanager/" + tripmanagerId + "/assignments";
    }

    @PostMapping("/{tripmanagerId}/assignment/{assignmentId}/delete")
    public String deleteAssignmentFromDriver(@PathVariable String tripmanagerId, @PathVariable String assignmentId) {
        TripManager tripManager = tripManagerService.getTripManagerById(tripmanagerId);
        DutyAssignment assignmentToDelete = dutyAssignmentsService.getAssignmentById(assignmentId);

        if(tripManager != null && assignmentToDelete != null) {
            tripManager.getAssignments().remove(assignmentToDelete);
            tripManagerService.updateTripManager(tripmanagerId, tripManager);
        }

        return "redirect:/tripmanager/" + tripmanagerId + "/assignments";
    }
}