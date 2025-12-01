package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.model.BusStatus;
import com.example.busmanagementsystem.service.databaseServices.BusDatabaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bus")
public class BusController {

    private final BusDatabaseService busService;

    @Autowired
    public BusController(BusDatabaseService busService) {
        this.busService = busService;
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
        return "bus/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Bus existingBus = busService.findById(id);
        if (existingBus != null) {
            model.addAttribute("bus", existingBus);
            model.addAttribute("statusOptions", BusStatus.values());
            return "bus/form";
        }
        return "redirect:/bus";
    }


    @PostMapping("/save")
    public String saveBus(@Valid @ModelAttribute("bus") Bus bus,
                          BindingResult bindingResult,
                          Model model,
                          @RequestParam(defaultValue = "false") boolean isEditMode) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("statusOptions", BusStatus.values());
            model.addAttribute("isEditMode", isEditMode);
            return "bus/form";
        }
        if (!isEditMode && busService.findById(bus.getId()) != null) {
            bindingResult.rejectValue("id", "error.bus", "Acest ID există deja în baza de date!");
        }


        try {
            busService.save(bus);
        } catch (DuplicateAttributeException e) {
            bindingResult.rejectValue("registrationNumber", "error.bus", e.getMessage());
            model.addAttribute("statusOptions", BusStatus.values());
            return "bus/form";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
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