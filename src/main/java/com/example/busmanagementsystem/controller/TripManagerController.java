package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.model.TripManager;
import com.example.busmanagementsystem.service.TripManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tripmanager")
public class TripManagerController {
    private final TripManagerService tripManagerService;

    @Autowired
    public TripManagerController(TripManagerService service) {
        this.tripManagerService = service;
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

    @PostMapping
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
}
