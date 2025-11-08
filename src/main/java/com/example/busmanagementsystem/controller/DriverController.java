package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.model.Driver;
import com.example.busmanagementsystem.model.TripManager;
import com.example.busmanagementsystem.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/driver")
public class DriverController {

    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public String getAllDrivers(Model model) {

        model.addAttribute("drivers", driverService.findAllDrivers().values());
        return "driver/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {

        model.addAttribute("driver", new Driver());
        return "driver/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Driver existingDriver = driverService.getDriverById(id);
        if (existingDriver != null) {
            model.addAttribute("driver", existingDriver);
            return "driver/form";
        }

        return "redirect:/driver";
    }

    @PostMapping("/{id}")
    public String updateDriver(@PathVariable String id,
                               @RequestParam String name,
                               @RequestParam String yearsOfExperience){

        Driver existingDriver = driverService.getDriverById(id);

        if (existingDriver != null) {
            existingDriver.setName(name);
            existingDriver.setYearsOfExperience(yearsOfExperience);

            driverService.updateDriver(id,  existingDriver);
        }

        return "redirect:/driver";
    }

    @PostMapping("/create")
    public String createDriver(@RequestParam String id, @RequestParam String name, @RequestParam String yearsOfExperience) {

        driverService.addDriver(new Driver(id, name, yearsOfExperience));
        return "redirect:/driver";
    }

    @PostMapping("/{id}/delete")
    public String deleteDriver(@PathVariable String id) {
        driverService.deleteDriver(id);
        return "redirect:/driver";
    }
}