package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.exceptions.EntityNotFoundException;
import com.example.busmanagementsystem.model.DutyAssignment;
import com.example.busmanagementsystem.model.Role;
import com.example.busmanagementsystem.service.databaseServices.DriverDatabaseService;
import com.example.busmanagementsystem.service.databaseServices.DutyAssignmentsDatabaseService;
import com.example.busmanagementsystem.service.databaseServices.BusTripDatabaseService;
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
import org.springframework.ui.Model;

@Controller
@RequestMapping("/duty-assignment")
public class DutyAssignmentController {

    private final DutyAssignmentsDatabaseService dutyAssignmentService;
    private final BusTripDatabaseService busTripService;
    private final DriverDatabaseService driverService;

    @Autowired
    public DutyAssignmentController(DutyAssignmentsDatabaseService dutyAssignmentsService,
                                    BusTripDatabaseService busTripService,
                                    DriverDatabaseService driverService) {
        this.dutyAssignmentService = dutyAssignmentsService;
        this.busTripService = busTripService;
        this.driverService = driverService;
    }

    @GetMapping
    public String getAllDutyAssignments(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String tripId,
            @RequestParam(required = false) String staffId,
            @RequestParam(required = false) Role role,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            Model model) {

        Page<DutyAssignment> page = dutyAssignmentService.findAllAssignmentsPageable(
                check(id),
                check(tripId),
                check(staffId),
                role,
                pageable
        );

        model.addAttribute("dutyAssignmentPage", page);
        model.addAttribute("dutyAssignments", page.getContent());
        model.addAttribute("pageable", pageable);
        model.addAttribute("filterId", id);
        model.addAttribute("filterTripId", tripId);
        model.addAttribute("filterStaffId", staffId);
        model.addAttribute("filterRole", role);
        model.addAttribute("roleOptions", Role.values());

        return "dutyAssignment/index";
    }

    private String check(String s) {
        if (s == null || s.trim().isEmpty()) {
            return null;
        }
        return s.trim();
    }


    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("dutyAssignment", new DutyAssignment());
        model.addAttribute("isEditMode", false);
        populateDropdowns(model);
        return "dutyAssignment/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        DutyAssignment existing = dutyAssignmentService.getAssignmentById(id);
        if (existing != null) {
            model.addAttribute("dutyAssignment", existing);
            model.addAttribute("isEditMode", true);
            populateDropdowns(model);
            return "dutyAssignment/form";
        }
        return "redirect:/duty-assignment";
    }

    @PostMapping("/{id}")
    public String updateDutyAssignment(@PathVariable String id,
                                       @Valid @ModelAttribute("dutyAssignment") DutyAssignment dutyAssignment,
                                       BindingResult bindingResult,
                                       Model model) {
        dutyAssignment.setId(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("isEditMode", true);
            populateDropdowns(model);
            return "dutyAssignment/form";
        }

        try {
            dutyAssignmentService.updateAssignment(id, dutyAssignment);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("isEditMode", true);
            populateDropdowns(model);
            return "dutyAssignment/form";
        }

        return "redirect:/duty-assignment";
    }

    @PostMapping("/create")
    public String createDutyAssignment(@Valid @ModelAttribute("dutyAssignment") DutyAssignment dutyAssignment,
                                       BindingResult bindingResult,
                                       Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("isEditMode", false);
            populateDropdowns(model);
            return "dutyAssignment/form";
        }

        try {
            dutyAssignmentService.addAssignment(dutyAssignment);
        } catch (DuplicateAttributeException e) {
            bindingResult.rejectValue("id", "error.dutyAssignment", e.getMessage());
            model.addAttribute("isEditMode", false);
            populateDropdowns(model);
            return "dutyAssignment/form";
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("isEditMode", false);
            populateDropdowns(model);
            return "dutyAssignment/form";
        }

        return "redirect:/duty-assignment";
    }

    @PostMapping("/{id}/delete")
    public String deleteDutyAssignment(@PathVariable String id) {
        dutyAssignmentService.deleteAssignment(id);
        return "redirect:/duty-assignment";
    }

    private void populateDropdowns(Model model) {
        model.addAttribute("roles", Role.values());
        model.addAttribute("availableTrips", busTripService.findAll().values());
        model.addAttribute("availableStaff", driverService.findAllDrivers().values());
    }
}