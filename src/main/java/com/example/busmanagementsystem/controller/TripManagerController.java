package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.model.DutyAssignment;
import com.example.busmanagementsystem.model.TripManager;
import com.example.busmanagementsystem.service.databaseServices.DutyAssignmentsDatabaseService;
import com.example.busmanagementsystem.service.databaseServices.TripManagerDatabaseService;
import com.example.busmanagementsystem.service.inFileServices.DutyAssignmentsService;
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
@RequestMapping("/tripmanager")
public class TripManagerController {
//    private final TripManagerService tripManagerService;
//    private final DutyAssignmentsService dutyAssignmentsService;
    private final TripManagerDatabaseService tripManagerService;
    private final DutyAssignmentsDatabaseService dutyAssignmentsService;
    private final Validator validator;

    @Autowired
    public TripManagerController(TripManagerDatabaseService tripManagerService,
                                 DutyAssignmentsDatabaseService dutyAssignmentsService,
                                 Validator validator) {
        this.tripManagerService = tripManagerService;
        this.dutyAssignmentsService = dutyAssignmentsService;
        this.validator = validator;
    }

//    @Autowired
//    public TripManagerController(TripManagerService service, DutyAssignmentsService dutyAssignmentsService, Validator validator) {
//        this.tripManagerService = service;
//        this.dutyAssignmentsService = dutyAssignmentsService;
//        this.validator = validator;
//    }

    @GetMapping
    public String getAllTripManagers(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String employeeCode,
            @RequestParam(required = false) Integer minAssignments,
            @RequestParam(required = false) Integer maxAssignments,
            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
            Model model) {

        Page<TripManager> tripManagerPage = tripManagerService.findTripManagersByCriteria(
                id, name, employeeCode, minAssignments, maxAssignments, pageable
        );

        model.addAttribute("tripManagerPage", tripManagerPage);
        model.addAttribute("tripManagers", tripManagerPage.getContent());
        model.addAttribute("pageable", pageable);
        model.addAttribute("filterId", id);
        model.addAttribute("filterName", name);
        model.addAttribute("filterCode", employeeCode);
        model.addAttribute("filterMinAssign", minAssignments);
        model.addAttribute("filterMaxAssign", maxAssignments);

        return "tripmanager/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("tripmanager", new TripManager());
        model.addAttribute("isEditMode", false);
        return "tripmanager/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        TripManager existingManager = tripManagerService.getTripManagerById(id);

        if (existingManager != null) {
            model.addAttribute("tripmanager", existingManager);
            model.addAttribute("isEditMode", true);
            return "tripmanager/form";
        }
        return "redirect:/tripmanager";
    }

    @PostMapping("/{id}")
    public String updateTripManager(@PathVariable String id,
                                    @RequestParam String name,
                                    @RequestParam String employeeCode,
                                    Model model) {

        TripManager existingManager = tripManagerService.getTripManagerById(id);

        if (existingManager != null) {
            String oldName = existingManager.getName();
            String oldCode = existingManager.getEmployeeCode();

            existingManager.setName(name);
            existingManager.setEmployeeCode(employeeCode);

            DataBinder binder = new DataBinder(existingManager, "tripmanager");
            binder.setValidator(validator);
            binder.validate();
            BindingResult bindingResult = binder.getBindingResult();

            if (bindingResult.hasErrors()) {
                model.addAttribute("org.springframework.validation.BindingResult.tripmanager", bindingResult);

                model.addAttribute("tripmanager", existingManager);
                model.addAttribute("isEditMode", true);
                return "tripmanager/form";
            }

            try {
                tripManagerService.updateTripManager(id, existingManager);
            }
            catch (DuplicateAttributeException e) {
                existingManager.setName(name);
                existingManager.setEmployeeCode(employeeCode);
                model.addAttribute("errorMessage", e.getMessage());
                model.addAttribute("errorField", e.getAttributeName());
                model.addAttribute("tripmanager", existingManager);
                model.addAttribute("isEditMode", true);
                return "tripmanager/form";
            }
        }
        return "redirect:/tripmanager";
    }

    @PostMapping("/create")
    public String createTripManager(@RequestParam String id,
                                    @RequestParam String name,
                                    @RequestParam String employeeCode,
                                    Model model) {

        TripManager newTripManager = new TripManager(id, name, employeeCode);

        DataBinder binder = new DataBinder(newTripManager, "tripmanager");
        binder.setValidator(validator);
        binder.validate();
        BindingResult bindingResult = binder.getBindingResult();

        if (bindingResult.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.tripmanager", bindingResult);

            model.addAttribute("tripmanager", newTripManager);
            model.addAttribute("isEditMode", false);
            return "tripmanager/form";
        }

        try {
            tripManagerService.addTripManager(newTripManager);
        }
        catch (DuplicateAttributeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("errorField", e.getAttributeName());
            model.addAttribute("tripmanager", newTripManager);
            model.addAttribute("isEditMode", false);
            return "tripmanager/form";
        }

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
            model.addAttribute("isEditMode", false);

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