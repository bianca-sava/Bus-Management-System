package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bus") // Setează ruta de bază pentru toate metodele din această clasă
public class BusController {

    private final BusService busService;

    @Autowired
    public BusController(BusService busService) {
        this.busService = busService;
    }

    /**
     * Implementează GET /bus
     * Afișează lista cu toate autobuzele. [cite: 64-65]
     */
    @GetMapping
    public String getAllBuses(Model model) {
        // Service-ul tău returnează un Map, dar Thymeleaf iterează mai ușor pe o Colecție.
        // .values() ne dă o colecție cu toate autobuzele.
        model.addAttribute("buses", busService.findAll().values());

        // Returnează calea către template: /resources/templates/bus/index.html [cite: 74]
        return "bus/index";
    }

}
