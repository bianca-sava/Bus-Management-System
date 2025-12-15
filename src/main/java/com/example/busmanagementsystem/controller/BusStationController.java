package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.model.BusStation;
import com.example.busmanagementsystem.model.BusTrip;
import com.example.busmanagementsystem.service.databaseServices.BusStationDatabaseService;
import com.example.busmanagementsystem.service.databaseServices.BusTripDatabaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bus-station")
public class BusStationController {

    private final BusStationDatabaseService busStationService;
    private final BusTripDatabaseService busTripService;

    @Autowired
    public BusStationController(BusTripDatabaseService busTripService, BusStationDatabaseService busStationService) {
        this.busTripService = busTripService;
        this.busStationService = busStationService;
    }

    @GetMapping
    public String getAllBusStations(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable,
            Model model) {

        Page<BusStation> busStationPage = busStationService.findAllPageable(
                check(id),
                check(name),
                check(city),
                pageable
        );

        model.addAttribute("busStationPage", busStationPage);
        model.addAttribute("busStations", busStationPage.getContent());
        model.addAttribute("pageable", pageable);

        model.addAttribute("filterId", id);
        model.addAttribute("filterName", name);
        model.addAttribute("filterCity", city);

        return "busStation/index";
    }

    private String check(String s) {
        if (s == null || s.trim().isEmpty()) {
            return null;
        }
        return s.trim();
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("busStation", new BusStation());
        model.addAttribute("isEditMode", false);
        return "busStation/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        BusStation existing = busStationService.findById(id);
        if (existing != null) {
            model.addAttribute("busStation", existing);
            model.addAttribute("isEditMode", true);
            return "busStation/form";
        }
        return "redirect:/bus-station";
    }

    @PostMapping("/{id}")
    public String updateBusStation(@PathVariable String id,
                                   @Valid @ModelAttribute("busStation") BusStation busStation,
                                   BindingResult bindingResult,
                                   Model model) {

        busStation.setId(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("isEditMode", true);
            return "busStation/form";
        }

        busStationService.update(id, busStation);
        return "redirect:/bus-station";
    }

    @PostMapping("/create")
    public String createBusStation(@Valid @ModelAttribute("busStation") BusStation busStation,
                                   BindingResult bindingResult,
                                   Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("isEditMode", false); // Rămânem pe creare în caz de eroare
            return "busStation/form";
        }

        try {
            busStationService.create(busStation);
        } catch (DuplicateAttributeException e) {
            bindingResult.rejectValue("id", "error.busStation", e.getMessage());
            model.addAttribute("isEditMode", false);
            return "busStation/form";
        }

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