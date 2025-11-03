package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.model.TripManager;
import com.example.busmanagementsystem.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tripmanager")
public class TripManagerController {
    private final StaffService tripManagerService;

    @Autowired
    public TripManagerController(StaffService service) {
        this.tripManagerService = service;
    }

    @GetMapping
    public String getAllTripManagers(Model model) {
        model.addAttribute("tripmanagers", tripManagerService.getAllTripManagers().values());

        return "tripmanager/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("tripmanager", new TripManager());

        return "tripmanager/form";
    }

    @PostMapping
    public String createTripManager(@RequestParam String id,
                            @RequestParam String name,
                            @RequestParam String employeeCode) {

        TripManager newTripManager = new TripManager(id, name, employeeCode);


        tripManagerService.addStaff(newTripManager);

        return "redirect:/tripmanager";
    }

    @PostMapping("/{id}/delete")
    public String deleteTripManager(@PathVariable String id) {
        tripManagerService.deleteStaff(id);

        return "redirect:/tripmanager";
    }
}
