package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.model.BusTrip;
import com.example.busmanagementsystem.service.BusTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bus-trip")
public class BusTripController {

    private final BusTripService busTripService;
    @Autowired
    public BusTripController(BusTripService busTripService) {
        this.busTripService = busTripService;
    }

    @GetMapping
    public String getAllBusTrips(Model model) {

        model.addAttribute("busTrips", busTripService.findAll().values());

        return "busTrip/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {

        model.addAttribute("busTrip", new BusTrip());

        return "busTrip/form";
    }

    @PostMapping
    public String createBusTrip(@RequestParam String id, @RequestParam String routeId, @RequestParam String busId, @RequestParam String startTime) {
        busTripService.create(new BusTrip(id, routeId, busId, startTime));
        return "redirect:/bus-trip";
    }

    @PostMapping("/{id}/delete")
    public String deleteBusTrip(@PathVariable String id) {
        busTripService.delete(id);
        return "redirect:/bus-trip";
    }

}
