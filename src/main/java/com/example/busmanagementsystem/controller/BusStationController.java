package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.model.BusStation;
import com.example.busmanagementsystem.service.BusStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bus-station")
public class BusStationController {

    private final BusStationService busStationService;

    @Autowired
    public BusStationController(BusStationService busStationService) {
        this.busStationService = busStationService;
    }

    @GetMapping
    public String getAllBusStations(Model model) {

        model.addAttribute("busStations", busStationService.findAll().values());

        return "busStation/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {

        model.addAttribute("busStation", new BusStation());

        return "busStation/form";
    }


    @PostMapping
    public String createBusStation(@RequestParam String id,@RequestParam String name, @RequestParam String city) {
        busStationService.create(new BusStation(id, name, city));
        return "redirect:/bus-station";
    }

    @PostMapping("/{id}/delete")
    public String deleteBusStation(@PathVariable String id) {
        busStationService.delete(id);
        return "redirect:/bus-station";
    }
}