package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.model.BusStation;
import com.example.busmanagementsystem.model.BusTrip; // <-- Import Adăugat
import com.example.busmanagementsystem.model.Route;
import com.example.busmanagementsystem.service.BusStationService;
import com.example.busmanagementsystem.service.BusTripService; // <-- Import Adăugat
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
    private final BusTripService busTripService;

    @Autowired
    public RouteController(RouteService routeService,
                           BusStationService busStationService,
                           BusTripService busTripService) {
        this.routeService = routeService;
        this.busStationService = busStationService;
        this.busTripService = busTripService;
    }


    @GetMapping
    public String getAllRoutes(Model model) {
        model.addAttribute("routes", routeService.findAll().values());
        return "route/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("route", new Route());
        model.addAttribute("allBusStations", busStationService.findAll().values());
        return "route/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Route existingRoute = routeService.findById(id);
        if (existingRoute != null) {
            model.addAttribute("route", existingRoute);
            model.addAttribute("allBusStations", busStationService.findAll().values());
            return "route/form";
        }
        return "redirect:/route";
    }

    @PostMapping("/{id}")
    public String updateRoute(@PathVariable String id,
                              @RequestParam String originId,
                              @RequestParam String destinationId,
                              @RequestParam double distance,
                              @RequestParam int nrOfStations) {
        Route existingRoute = routeService.findById(id);
        if (existingRoute != null) {
            BusStation origin = busStationService.findById(originId);
            BusStation destination = busStationService.findById(destinationId);
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
        Route newRoute = new Route(id, origin, destination, distance, nrOfStations);
        routeService.create(newRoute);
        return "redirect:/route";
    }

    @PostMapping("/{id}/delete")
    public String deleteRoute(@PathVariable String id) {
        routeService.delete(id);
        return "redirect:/route";
    }


    @GetMapping("/{id}/trips")
    public String getRouteTrips(@PathVariable String id, Model model) {
        Route route = routeService.findById(id);
        if (route != null) {
            model.addAttribute("trips", route.getTrips());
            model.addAttribute("routeId", route.getId());
            model.addAttribute("routeName", route.getOrigin().getName() + " - " + route.getDestination().getName());
            return "route/trips";
        }
        return "redirect:/route";
    }

    @GetMapping("/{id}/trips/new")
    public String showAssignTripForm(@PathVariable String id, Model model) {
        Route route = routeService.findById(id);
        if (route != null) {
            model.addAttribute("routeId", id);
            model.addAttribute("availableTrips", busTripService.findAll().values());
            return "route/assignTrip";
        }
        return "redirect:/route";
    }

    @PostMapping("/{routeId}/trips/add")
    public String addTripToRoute(@PathVariable String routeId, @RequestParam String selectedTripId) {
        Route route = routeService.findById(routeId);
        BusTrip tripToAdd = busTripService.findById(selectedTripId);

        if (route != null && tripToAdd != null) {
            route.getTrips().add(tripToAdd);
            routeService.update(routeId, route);
        }
        return "redirect:/route/" + routeId + "/trips";
    }

    @PostMapping("/{routeId}/trips/{tripId}/delete")
    public String deleteTripFromRoute(@PathVariable String routeId, @PathVariable String tripId) {
        Route route = routeService.findById(routeId);
        if (route != null) {
            route.getTrips().removeIf(trip -> trip.getId().equals(tripId));
            routeService.update(routeId, route);
        }
        return "redirect:/route/" + routeId + "/trips";
    }
}