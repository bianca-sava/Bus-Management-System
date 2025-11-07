package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.model.BusStation;
import com.example.busmanagementsystem.model.Route;
import com.example.busmanagementsystem.service.BusStationService;
import com.example.busmanagementsystem.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/route")
public class RouteController {
    private final RouteService routeService;
    private final BusStationService busStationService;

    @Autowired
    public RouteController(RouteService routeService, BusStationService busStationService) {
        this.routeService = routeService;
        this.busStationService = busStationService;
    }

    @GetMapping
    public String getAllRoutes(Model model) {
        model.addAttribute("routes", routeService.findAll().values());
        return "route/index";
    }

    // --- UPDATED METHOD ---
    // Renamed and updated to provide necessary data for the form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        // 1. Add a new Route object for th:object binding
        model.addAttribute("route", new Route());

        // 2. Add all available bus stations for the dropdowns
        model.addAttribute("allBusStations", busStationService.findAll().values());

        return "route/form";
    }

    // --- NEW METHOD (Added for Edit) ---
    // This shows the form for an *existing* route
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {

        Route existingRoute = routeService.findById(id);

        if (existingRoute != null) {
            // 1. Add the *existing* route for th:object binding
            model.addAttribute("route", existingRoute);

            // 2. Add all stations for the dropdowns (still needed for options)
            model.addAttribute("allBusStations", busStationService.findAll().values());

            return "route/form";
        }

        return "redirect:/route";
    }

    // --- NEW METHOD (Added for Update) ---
    // This handles the POST submission from the *edit* form
    @PostMapping("/{id}")
    public String updateRoute(@PathVariable String id,
                              @RequestParam String originId,
                              @RequestParam String destinationId,
                              @RequestParam double distance,
                              @RequestParam int nrOfStations) {

        Route existingRoute = routeService.findById(id);

        if (existingRoute != null) {
            // Find the BusStation objects from the IDs
            BusStation origin = busStationService.findById(originId);
            BusStation destination = busStationService.findById(destinationId);

            // Update the fields on the *existing* object
            // This preserves other fields like the 'trips' list
            existingRoute.setOrigin(origin);
            existingRoute.setDestination(destination);
            existingRoute.setDistance(distance);
            existingRoute.setNrOfStations(nrOfStations);

            routeService.update(id, existingRoute);
        }

        return "redirect:/route";
    }


    @PostMapping("/create")
    public String createRoute(@RequestParam String id,
                              @RequestParam String originId,
                              @RequestParam String destinationId,
                              @RequestParam double distance,
                              @RequestParam int nrOfStations) {

        BusStation origin = busStationService.findById(originId);
        BusStation destination = busStationService.findById(destinationId);

        // This constructor is correct for a new route
        Route newRoute = new Route(id, origin, destination, distance, nrOfStations);

        routeService.create(newRoute);

        return "redirect:/route";
    }

    @PostMapping("/{id}/delete")
    public String deleteRoute(@PathVariable String id) {
        routeService.delete(id);
        return "redirect:/route";
    }
}