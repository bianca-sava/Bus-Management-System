package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.model.BusStation;
import com.example.busmanagementsystem.model.BusTrip;
import com.example.busmanagementsystem.service.databaseServices.BusStationDatabaseService;
import com.example.busmanagementsystem.service.databaseServices.BusTripDatabaseService;
import com.example.busmanagementsystem.service.inFileServices.BusStationService;
import com.example.busmanagementsystem.service.inFileServices.BusTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bus-station")
public class BusStationController {

//    private final BusStationService busStationService;
//    private final BusTripService busTripService;
    private final BusStationDatabaseService busStationService;
    private final BusTripDatabaseService busTripService;

    @Autowired
    public BusStationController(BusTripDatabaseService busTripService, BusStationDatabaseService busStationService) {
        this.busTripService = busTripService;
        this.busStationService = busStationService;
    }

//    @Autowired
//    public BusStationController(BusStationService busStationService,  BusTripService busTripService) {
//        this.busStationService = busStationService;
//        this.busTripService = busTripService;
//    }

    @GetMapping
    public String getAllBusStations(Model model) {

        model.addAttribute("busStations", busStationService.findAll().values());

        return "busStation/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {

        model.addAttribute("busStation", new BusStation());

        return "busStation/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        BusStation existingBusStation = busStationService.findById(id);

        if (existingBusStation != null) {
            model.addAttribute("busStation", existingBusStation);
            return "busStation/form";
        }

        return "redirect:/bus-station";
    }

    @PostMapping("/{id}")
    public String updateBusStation(@RequestParam String id,
                                   @RequestParam String name,
                                   @RequestParam String city) {
        BusStation existingBusStation = busStationService.findById(id);
        if(existingBusStation != null) {
            existingBusStation.setName(name);
            existingBusStation.setCity(city);

            busStationService.update(id, existingBusStation);
        }

        return "redirect:/bus-station";
    }

    @PostMapping("/create")
    public String createBusStation(@RequestParam String id,@RequestParam String name, @RequestParam String city) {
        busStationService.create(new BusStation(id, name, city));
        return "redirect:/bus-station";
    }

    @PostMapping("/{id}/delete")
    public String deleteBusStation(@PathVariable String id) {
        busStationService.delete(id);
        return "redirect:/bus-station";
    }

    @GetMapping("/{id}/trips")
    public String getStationTrips(@PathVariable String id, Model model) {
        BusStation station = busStationService.findById(id);

        if (station != null) {
            model.addAttribute("trips", station.getTrips());
            model.addAttribute("stationId", station.getId());
            model.addAttribute("stationName", station.getName());

            return "busStation/trips";
        }

        return "redirect:/bus-station";
    }

    @GetMapping("/{id}/trips/new")
    public String showAssignTripForm(@PathVariable String id, Model model) {
        BusStation station = busStationService.findById(id);

        if (station != null) {
            model.addAttribute("stationId", id);

            model.addAttribute("availableTrips", busTripService.findAll().values());

            return "busStation/assignTrip";
        }
        return "redirect:/bus-station";
    }

    @PostMapping("/{stationId}/trips/add")
    public String addTripToStation(@PathVariable String stationId, @RequestParam String selectedTripId) {
        BusStation station = busStationService.findById(stationId);
        BusTrip tripToAdd = busTripService.findById(selectedTripId);

        if (station != null && tripToAdd != null) {
            station.getTrips().add(tripToAdd);

            busStationService.update(stationId, station);
        }

        return "redirect:/bus-station/" + stationId + "/trips";
    }

    @PostMapping("/{stationId}/trips/{tripId}/delete")
    public String deleteTripFromStation(@PathVariable String stationId, @PathVariable String tripId) {
        BusStation station = busStationService.findById(stationId);

        if (station != null) {
            station.getTrips().removeIf(trip -> trip.getId().equals(tripId));
            busStationService.update(stationId, station);
        }

        return "redirect:/bus-station/" + stationId + "/trips";
    }
}