package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bus")
public class BusController {

    private final BusService busService;

    @Autowired
    public BusController(BusService busService) {
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

        return "bus/form";
    }

    @PostMapping
    public String createBus(@RequestParam String id,
                            @RequestParam String registrationNumber,
                            @RequestParam int capacity) {

        Bus newBus = new Bus(id, registrationNumber, capacity);

        busService.create(newBus);

        return "redirect:/bus";
    }

    @PostMapping("/{id}/delete")
    public String deleteBus(@PathVariable String id) {
        busService.delete(id);

        return "redirect:/bus";
    }
}