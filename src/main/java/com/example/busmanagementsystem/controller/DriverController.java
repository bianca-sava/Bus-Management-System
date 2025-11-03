package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.model.Driver;
import com.example.busmanagementsystem.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/driver")
public class DriverController {

    private final StaffService driverService;

    @Autowired
    public DriverController(StaffService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public String getAllDrivers(Model model) {

        model.addAttribute("drivers", driverService.getAllDrivers().values());
        return "driver/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {

        model.addAttribute("driver", new Driver());
        return "driver/form";
    }

    @PostMapping
    public String createDriver(@RequestParam String id, @RequestParam String name, @RequestParam String yearsOfExperience) {

        driverService.addStaff(new Driver(id, name, yearsOfExperience));
        return "redirect:/driver";
    }

    @PostMapping("/{id}/delete")
    public String deleteDriver(@PathVariable String id) {
        driverService.deleteStaff(id);
        return "redirect:/driver";
    }
}