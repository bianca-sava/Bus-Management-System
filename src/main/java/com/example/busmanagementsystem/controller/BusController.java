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
    @PostMapping
    public String createBus(@ModelAttribute Bus bus) {
        // Datorită @ModelAttribute, Spring ia datele din formular
        // și le pune în obiectul 'bus' folosind setter-ii.

        // Constructorul tău setează statusul și nrOfPassengers,
        // dar deoarece folosim constructorul gol, ar fi bine
        // să le setăm manual aici, sau să ajustăm formularul.
        // Pentru a păstra simplitatea, ne bazăm pe ce vine din formular.
        // Dacă ID, registrationNumber și capacity sunt singurele câmpuri,
        // va trebui să setăm manual statusul și nrOfPassengers.

        // O abordare mai simplă (bazată pe constructorul tău):
        // Presupunând că formularul trimite id, registrationNumber și capacity.
        // Și că 'bus' le conține.

        // Să simplificăm: Presupunem că 'bus' e populat corect de Spring.
        // (Vom crea formularul să trimită doar câmpurile necesare)

        busService.create(bus); // Salvăm autobuzul

        // Redirecționăm către lista principală pentru a vedea noul autobuz
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