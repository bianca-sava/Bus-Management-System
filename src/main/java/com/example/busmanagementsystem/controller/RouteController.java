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

    @GetMapping("/new")
    public String createRoute(Model model) {

        return "route/form";
    }

    @PostMapping
    public String createRoute(@RequestParam String id,
                              @RequestParam String originId,
                              @RequestParam String destinationId,
                              @RequestParam double distance,
                              @RequestParam int nrOfStations) {

         BusStation origin = busStationService.findById(originId);
         BusStation destination = busStationService.findById(destinationId);

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
