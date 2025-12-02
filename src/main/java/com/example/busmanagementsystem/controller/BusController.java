package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.model.BusStatus;
import com.example.busmanagementsystem.service.databaseServices.BusDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bus")
public class BusController {

    private final BusDatabaseService busService;
    private final Validator validator;

    @Autowired
    public BusController(BusDatabaseService busService, Validator validator) {
        this.busService = busService;
        this.validator = validator;
    }

    @GetMapping
    public String getAllBuses(Model model) {
        model.addAttribute("buses", busService.findAll().values());
        return "bus/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("bus", new Bus());
        model.addAttribute("statusOptions", BusStatus.values());
        model.addAttribute("isEditMode", false);
        return "bus/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Bus existingBus = busService.findById(id);
        if (existingBus != null) {
            model.addAttribute("bus", existingBus);
            model.addAttribute("statusOptions", BusStatus.values());
            model.addAttribute("isEditMode", true);
            return "bus/form";
        }
        return "redirect:/bus";
    }

    @PostMapping("/{id}")
    public String updateBus(@PathVariable String id,
                            @RequestParam String registrationNumber,
                            @RequestParam int capacity,
                            @RequestParam BusStatus status,
                            @RequestParam int nrOfPassengers,
                            Model model) {

        Bus existingBus = busService.findById(id);

        if (existingBus != null) {
            existingBus.setNrOfPassengers(nrOfPassengers);
            existingBus.setCapacity(capacity);
            existingBus.setStatus(status);
            existingBus.setRegistrationNumber(registrationNumber);

            DataBinder binder = new DataBinder(existingBus, "bus");
            binder.setValidator(validator);
            binder.validate();
            BindingResult bindingResult = binder.getBindingResult();

            if (bindingResult.hasErrors()) {
                model.addAttribute("org.springframework.validation.BindingResult.bus", bindingResult);
                model.addAttribute("bus", existingBus);
                model.addAttribute("isEditMode", true);

                model.addAttribute("statusOptions", BusStatus.values());

                return "bus/form";
            }

            try {
                busService.update(id, existingBus);
            }
            catch (DuplicateAttributeException | IllegalArgumentException e) {
                model.addAttribute("errorMessage", e.getMessage());
                if (e instanceof DuplicateAttributeException) {
                    model.addAttribute("errorField", ((DuplicateAttributeException) e).getAttributeName());
                }

                model.addAttribute("bus", existingBus);
                model.addAttribute("isEditMode", true);

                model.addAttribute("statusOptions", BusStatus.values());

                return "bus/form";
            }
        }
        return "redirect:/bus";
    }

    @PostMapping("/create")
    public String createBus(@RequestParam String id,
                            @RequestParam String registrationNumber,
                            @RequestParam int capacity,
                            @RequestParam BusStatus status,
                            @RequestParam int nrOfPassengers,
                            Model model) {

        Bus newBus = new Bus(id, registrationNumber, capacity, status, nrOfPassengers);

        DataBinder binder = new DataBinder(newBus, "bus");
        binder.setValidator(validator);
        binder.validate();
        BindingResult bindingResult = binder.getBindingResult();

        if (bindingResult.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.bus", bindingResult);
            model.addAttribute("bus", newBus);
            model.addAttribute("isEditMode", false);

            model.addAttribute("statusOptions", BusStatus.values());

            return "bus/form";
        }

        try {
            busService.create(newBus);
        }
        catch (DuplicateAttributeException | IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            if (e instanceof DuplicateAttributeException) {
                model.addAttribute("errorField", ((DuplicateAttributeException) e).getAttributeName());
            }

            model.addAttribute("bus", newBus);
            model.addAttribute("isEditMode", false);

            model.addAttribute("statusOptions", BusStatus.values());

            return "bus/form";
        }

        return "redirect:/bus";
    }

    @PostMapping("/{id}/delete")
    public String deleteBus(@PathVariable String id) {
        busService.delete(id);
        return "redirect:/bus";
    }
}