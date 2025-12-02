package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.model.Driver;
import com.example.busmanagementsystem.model.DutyAssignment;
import com.example.busmanagementsystem.service.databaseServices.DriverDatabaseService;
import com.example.busmanagementsystem.service.databaseServices.DutyAssignmentsDatabaseService;
import com.example.busmanagementsystem.service.inFileServices.DriverService;
import com.example.busmanagementsystem.service.inFileServices.DutyAssignmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/driver")
public class DriverController {

//    private final DriverService driverService;
//    private final DutyAssignmentsService dutyAssignmentsService;
    private final DriverDatabaseService driverService;
    private final DutyAssignmentsDatabaseService dutyAssignmentsService;
    private final Validator validator;

    @Autowired
    public DriverController(DriverDatabaseService driverService, DutyAssignmentsDatabaseService dutyAssignmentsService,
                            Validator validator) {
        this.driverService = driverService;
        this.dutyAssignmentsService = dutyAssignmentsService;
        this.validator = validator;
    }

//    @Autowired
//    public DriverController(DriverService driverService, DutyAssignmentsService dutyAssignmentsService,
//                              Validator validator) {
//        this.driverService = driverService;
//        this.dutyAssignmentsService = dutyAssignmentsService;
//        this.validator = validator;
//    }

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
                               @RequestParam String name,
                               @RequestParam String yearsOfExperience,
                               Model model){

        Driver existingDriver = driverService.getDriverById(id);

        if (existingDriver != null) {
            existingDriver.setName(name);
            existingDriver.setYearsOfExperience(yearsOfExperience);

            DataBinder binder = new DataBinder(existingDriver, "dirver");
            binder.setValidator(validator);
            binder.validate();
            BindingResult bindingResult = binder.getBindingResult();

            if (bindingResult.hasErrors()) {
                model.addAttribute("org.springframework.validation.BindingResult.driver", bindingResult);

                model.addAttribute("driver", existingDriver);
                model.addAttribute("isEditMode", true);
                return "driver/form";
            }
            driverService.updateDriver(id,  existingDriver);
        }

        return "redirect:/driver";
    }

    @PostMapping("/create")
    public String createDriver(@RequestParam String id, @RequestParam String name, @RequestParam String yearsOfExperience,
                               Model model) {

        Driver newdriver = new Driver(id, name, yearsOfExperience);

        DataBinder binder = new DataBinder(newdriver, "dirver");
        binder.setValidator(validator);
        binder.validate();
        BindingResult bindingResult = binder.getBindingResult();

        if (bindingResult.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.driver", bindingResult);

            model.addAttribute("driver", newdriver);
            model.addAttribute("isEditMode", false);
            return "driver/form";
        }

        try{
            driverService.addDriver(newdriver);
        }
        catch (DuplicateAttributeException e){
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("errorField", e.getAttributeName());

            model.addAttribute("ticket", newdriver);
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
            model.addAttribute("drivarId", driver.getId());
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