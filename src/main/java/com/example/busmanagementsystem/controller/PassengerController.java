package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.model.Passenger;
import com.example.busmanagementsystem.service.PassengerService;
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
    public String showCreateForm(Model model) {
        model.addAttribute("passenger", new Passenger());
        return "passenger/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {

        Passenger existingPassenger = passengerService.findById(id);

        if (existingPassenger != null) {
            model.addAttribute("passenger", existingPassenger);
            return "passenger/form";
        }
        return "redirect:/passenger";
    }

    @PostMapping("/{id}")
    public String updatePassenger(@PathVariable String id,
                                  @RequestParam String name,
                                  @RequestParam String currency) {


        Passenger existingPassenger = passengerService.findById(id);

        if (existingPassenger != null) {

            existingPassenger.setName(name);
            existingPassenger.setCurrency(currency);

            passengerService.update(id, existingPassenger);
        }

        return "redirect:/passenger";
    }

    @PostMapping("/create")
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