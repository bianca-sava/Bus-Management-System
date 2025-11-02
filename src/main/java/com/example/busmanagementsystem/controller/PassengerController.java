package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.model.Passenger;
import com.example.busmanagementsystem.service.BusStationService;
import com.example.busmanagementsystem.service.PassengerService;
import com.example.busmanagementsystem.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/passenger")
public class PassengerController {
    private final PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping
    public String getAllPassengers(Model model) {
        model.addAttribute("passengers", passengerService.findAll().values());

        return "passenger/index";
    }

    @GetMapping("/new")
    public String createPassenger(Model model) {
        model.addAttribute("passenger", new Passenger());

        return "passenger/form";
    }

    @PostMapping
    public String createPassenger(@RequestParam String id,
                                  @RequestParam String name,
                                  @RequestParam String currency) {
        Passenger newPassenger = new Passenger(id, name, currency);

        passengerService.create(newPassenger);

        return "redirect:/passenger";
    }

    @PostMapping("/{id}/delete")
    public String deletePassenger(@PathVariable String id) {
        passengerService.delete(id);

        return "redirect:/passenger";
    }
}
