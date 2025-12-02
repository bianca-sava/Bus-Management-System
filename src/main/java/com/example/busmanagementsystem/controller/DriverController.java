package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.model.Driver;
import com.example.busmanagementsystem.model.DutyAssignment;
import com.example.busmanagementsystem.service.databaseServices.DriverDatabaseService;
import com.example.busmanagementsystem.service.databaseServices.DutyAssignmentsDatabaseService;
import jakarta.validation.Valid; // Asigură-te că ai importul acesta
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/driver")
public class DriverController {

    private final DriverDatabaseService driverService;
    private final DutyAssignmentsDatabaseService dutyAssignmentsService;

    @Autowired
    public DriverController(DriverDatabaseService driverService, DutyAssignmentsDatabaseService dutyAssignmentsService) {
        this.driverService = driverService;
        this.dutyAssignmentsService = dutyAssignmentsService;
    }

    @GetMapping
    public String getAllDrivers(Model model) {
        model.addAttribute("drivers", driverService.findAllDrivers().values());
        return "driver/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("driver", new Driver());
        model.addAttribute("isEditMode", false);
        return "driver/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Driver existingDriver = driverService.getDriverById(id);
        if (existingDriver != null) {
            model.addAttribute("driver", existingDriver);
            model.addAttribute("isEditMode", true);
            return "driver/form";
        }
        return "redirect:/driver";
    }

    @PostMapping("/{id}")
    public String updateDriver(@PathVariable String id,
                               @Valid @ModelAttribute("driver") Driver driver,
                               BindingResult bindingResult,
                               Model model) {

        driver.setId(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("isEditMode", true);
            return "driver/form";
        }

        Driver existingDriver = driverService.getDriverById(id);
        if (existingDriver != null) {
            driver.setAssignments(existingDriver.getAssignments()); // Păstrăm asignările existente
            driverService.updateDriver(id, driver);
        }

        return "redirect:/driver";
    }

    @PostMapping("/create")
    public String createDriver(@Valid @ModelAttribute("driver") Driver driver,
                               BindingResult bindingResult,
                               Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("isEditMode", false);
            return "driver/form";
        }

        try {
            driverService.addDriver(driver);
        } catch (DuplicateAttributeException e) {
            bindingResult.rejectValue("id", "error.driver", e.getMessage());

            model.addAttribute("isEditMode", false);
            return "driver/form";
        }

        return "redirect:/driver";
    }

    @PostMapping("/{id}/delete")
    public String deleteDriver(@PathVariable String id) {
        driverService.deleteDriver(id);
        return "redirect:/driver";
    }

    @GetMapping("/{id}/assignments")
    public String getDriverAssignments(@PathVariable String id, Model model) {
        Driver driver = driverService.getDriverById(id);

        if(driver != null) {
            model.addAttribute("assignments", driver.getAssignments());
            model.addAttribute("driverId", driver.getId());
            model.addAttribute("driverName", driver.getName());
            return "driver/assignments";
        }
        return "redirect:/driver";
    }

    @GetMapping("/{id}/assignments/new")
    public String showAssignAssignmentForm(@PathVariable String id, Model model) {
        Driver driver = driverService.getDriverById(id);

        if(driver != null) {
            model.addAttribute("driverId", id);
            model.addAttribute("availableAssignments", dutyAssignmentsService.getAllAssignments().values());

            return "driver/assignAssignment";
        }

        return "redirect:/driver";
    }

    @PostMapping("/{driverId}/assignments/add")
    public String addAssignmentToDriver(@PathVariable String driverId, @RequestParam String selectedAssignmentId) {
        Driver driver = driverService.getDriverById(driverId);
        DutyAssignment assignmentToAdd = dutyAssignmentsService.getAssignmentById(selectedAssignmentId);

        if(driver != null && assignmentToAdd != null) {
            driver.getAssignments().add(assignmentToAdd);

            driverService.updateDriver(driverId, driver);
        }

        return "redirect:/driver/" + driverId + "/assignments";
    }

    @PostMapping("/{driverId}/assignment/{assignmentId}/delete")
    public String deleteAssignmentFromDriver(@PathVariable String driverId, @PathVariable String assignmentId) {
        Driver driver = driverService.getDriverById(driverId);
        DutyAssignment assignmentToDelete = dutyAssignmentsService.getAssignmentById(assignmentId);

        if(driver != null && assignmentToDelete != null) {
            driver.getAssignments().remove(assignmentToDelete);
            driverService.updateDriver(driverId, driver);
        }

        return "redirect:/driver/" + driverId + "/assignments";
    }
}