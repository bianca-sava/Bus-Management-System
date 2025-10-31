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

    /**
     * Implementează GET /bus/new
     * Afișează formularul de creare. [cite: 66]
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        // Creăm un obiect Bus gol pe care formularul îl va popula.
        // Acesta este momentul în care Spring folosește constructorul gol (Pasul 0).
        model.addAttribute("bus", new Bus());

        // Returnează calea către template: /resources/templates/bus/form.html [cite: 75]
        return "bus/form";
    }

    /**
     * Implementează POST /bus
     * Procesează datele din formular și creează un autobuz nou. [cite: 66]
     */
    /**
     * Implementează POST /bus (Varianta 2 - Folosind constructorul cu argumente)
     * Procesează datele din formular și creează un autobuz nou.
     */
    @PostMapping
    public String createBus(@RequestParam String id,
                            @RequestParam String registrationNumber,
                            @RequestParam int capacity) {

        Bus newBus = new Bus(id, registrationNumber, capacity);


        busService.create(newBus);

        return "redirect:/bus";
    }

    /**
     * Implementează POST /bus/{id}/delete
     * Șterge autobuzul specificat. [cite: 67]
     */
    @PostMapping("/{id}/delete")
    public String deleteBus(@PathVariable String id) {
        // @PathVariable ia "id-ul" din URL
        busService.delete(id);

        // Redirecționăm către lista principală
        return "redirect:/bus";
    }
}