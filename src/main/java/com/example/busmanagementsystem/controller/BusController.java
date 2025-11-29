package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.model.BusStatus;
import com.example.busmanagementsystem.service.databaseServices.BusDatabaseService;
import com.example.busmanagementsystem.service.inFileServices.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bus")
public class BusController {

//    private final BusService busService;
    private final BusDatabaseService busService;

    @Autowired
    public BusController(BusDatabaseService busService) {
        this.busService = busService;
    }

//    @Autowired
//    public BusController(BusService busService) {
//        this.busService = busService;
//    }

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

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {

        model.addAttribute("statusOptions", BusStatus.values());

        Bus existingBus = busService.findById(id);

        if (existingBus != null) {
            model.addAttribute("bus", existingBus);
            return "bus/form";
        }
        return "redirect:/bus";
    }

    @PostMapping("/{id}")
    public String updateBus(@PathVariable String id,
                            @RequestParam String registrationNumber,
                            @RequestParam int capacity,
                            @RequestParam BusStatus status,
                            @RequestParam int nrOfPassengers) {

        Bus updatedBus = new Bus(id, registrationNumber, capacity, status, nrOfPassengers);

        busService.update(id, updatedBus);

        return "redirect:/bus";
    }

    @PostMapping("/create")
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