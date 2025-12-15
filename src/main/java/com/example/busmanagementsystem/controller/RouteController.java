package com.example.busmanagementsystem.controller;

import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.exceptions.EntityNotFoundException;
import com.example.busmanagementsystem.exceptions.InvalidRouteException;
import com.example.busmanagementsystem.model.BusStation;
import com.example.busmanagementsystem.model.BusTrip; // <-- Import Adăugat
import com.example.busmanagementsystem.model.Route;
import com.example.busmanagementsystem.service.databaseServices.BusStationDatabaseService;
import com.example.busmanagementsystem.service.databaseServices.BusTripDatabaseService;
import com.example.busmanagementsystem.service.databaseServices.RouteDatabaseService;
import com.example.busmanagementsystem.service.inFileServices.BusStationService;
import com.example.busmanagementsystem.service.inFileServices.BusTripService; // <-- Import Adăugat
import com.example.busmanagementsystem.service.inFileServices.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/route")
public class RouteController {
//    private final RouteService routeService;
//    private final BusStationService busStationService;
//    private final BusTripService busTripService;
    private final RouteDatabaseService routeService;
    private final BusStationDatabaseService busStationService;
    private final BusTripDatabaseService busTripService;
    private final Validator validator;

    @Autowired
    public RouteController(RouteDatabaseService routeService,
                           BusStationDatabaseService busStationService,
                           BusTripDatabaseService busTripService,
                           Validator validator) {
        this.routeService = routeService;
        this.busStationService = busStationService;
        this.busTripService = busTripService;
        this.validator = validator;
    }

//    @Autowired
//    public RouteController(RouteService routeService,
//                           BusStationService busStationService,
//                           BusTripService busTripService,
//                           Validator validator) {
//        this.routeService = routeService;
//        this.busStationService = busStationService;
//        this.busTripService = busTripService;
//        this.validator = validator;
//    }


    @GetMapping
    public String getAllRoutes(@RequestParam(required = false) String id,
                               @RequestParam(required = false) String origin,
                               @RequestParam(required = false) String destination,
                               @RequestParam(required = false) Double minDistance,
                               @RequestParam(required = false) Double maxDistance,
                               @RequestParam(required = false) Integer minStations,
                               @RequestParam(required = false) Integer maxStations,
                               @RequestParam(required = false) Integer minTrips,
                               @RequestParam(required = false) Integer maxTrips,
                               @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                               Model model) {

        Page<Route> routePage = routeService.findRoutesByCriteria(
                id, origin, destination, minDistance, maxDistance, minStations, maxStations, minTrips, maxTrips, pageable
        );

        model.addAttribute("routePage", routePage);
        model.addAttribute("routes", routePage.getContent());
        model.addAttribute("pageable", pageable);
        model.addAttribute("filterId", id);
        model.addAttribute("filterOrigin", origin);
        model.addAttribute("filterDest", destination);
        model.addAttribute("filterMinDist", minDistance);
        model.addAttribute("filterMaxDist", maxDistance);
        model.addAttribute("filterMinStations", minStations); // Nou
        model.addAttribute("filterMaxStations", maxStations); // Nou
        model.addAttribute("filterMinTrips", minTrips);
        model.addAttribute("filterMaxTrips", maxTrips);

        return "route/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("route", new Route());
        model.addAttribute("allBusStations", busStationService.findAll().values());
        model.addAttribute("isEditMode", false);
        return "route/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Route existingRoute = routeService.findById(id);
        if (existingRoute != null) {
            model.addAttribute("route", existingRoute);
            model.addAttribute("allBusStations", busStationService.findAll().values());
            model.addAttribute("isEditMode", true);
            return "route/form";
        }
        return "redirect:/route";
    }

    @PostMapping("/{id}")
    public String updateRoute(@PathVariable String id,
                              @RequestParam String originId,
                              @RequestParam String destinationId,
                              @RequestParam double distance,
                              @RequestParam int nrOfStations,
                              Model model) {


        Route existingRoute = routeService.findById(id);

        if (existingRoute != null) {
            BusStation origin = busStationService.findById(originId);
            BusStation destination = busStationService.findById(destinationId);

            existingRoute.setOrigin(origin);
            existingRoute.setDestination(destination);
            existingRoute.setDistance(distance);
            existingRoute.setNrOfStations(nrOfStations);

            DataBinder binder = new DataBinder(existingRoute, "route");
            binder.setValidator(validator);
            binder.validate();
            BindingResult bindingResult = binder.getBindingResult();

            if(bindingResult.hasErrors()) {
                model.addAttribute("org.springframework.validation.BindingResult.route", bindingResult);
                model.addAttribute("route", existingRoute);
                model.addAttribute("isEditMode", true);
                model.addAttribute("allBusStations", busStationService.findAll().values());
                return "route/form";
            }

            try {
                routeService.update(id, existingRoute);
            }
            catch (DuplicateAttributeException e){
                model.addAttribute("errorMessage", e.getMessage());
                model.addAttribute("errorField", e.getAttributeName());
                model.addAttribute("route", existingRoute);
                model.addAttribute("isEditMode", true);
                model.addAttribute("allBusStations", busStationService.findAll().values());
                return "route/form";
            }
            catch (InvalidRouteException e){
                model.addAttribute("errorMessage", e.getMessage());
                model.addAttribute("route", existingRoute);
                model.addAttribute("isEditMode", true);
                model.addAttribute("allBusStations", busStationService.findAll().values());
                return "route/form";
            }
            catch (EntityNotFoundException e){
                model.addAttribute("errorMessage", e.getMessage());
                model.addAttribute("errorField", e.getFieldName());
                model.addAttribute("route", existingRoute);
                model.addAttribute("isEditMode", true);
                model.addAttribute("allBusStations", busStationService.findAll().values());
                return "route/form";
            }
        }
        return "redirect:/route";
    }


    @PostMapping("/create")
    public String createRoute(@RequestParam String id,
                              @RequestParam String originId,
                              @RequestParam String destinationId,
                              @RequestParam double distance,
                              @RequestParam int nrOfStations,
                              Model model) {

        BusStation origin = busStationService.findById(originId);
        BusStation destination = busStationService.findById(destinationId);

        Route newRoute = new Route(id, origin, destination, distance, nrOfStations);

        DataBinder binder = new DataBinder(newRoute, "route");
        binder.setValidator(validator);
        binder.validate();
        BindingResult bindingResult = binder.getBindingResult();

        if(bindingResult.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.route", bindingResult);
            model.addAttribute("route", newRoute);
            model.addAttribute("isEditMode", false);
            model.addAttribute("allBusStations", busStationService.findAll().values());
            return "route/form";
        }

        try {
            routeService.create(newRoute);
        }
        catch (DuplicateAttributeException e){
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("errorField", e.getAttributeName());
            model.addAttribute("route", newRoute);
            model.addAttribute("isEditMode", false);
            model.addAttribute("allBusStations", busStationService.findAll().values());
            return "route/form";
        }
        catch (InvalidRouteException e){
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("route", newRoute);
            model.addAttribute("isEditMode", false);
            model.addAttribute("allBusStations", busStationService.findAll().values());
        }
        catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("errorField", e.getFieldName());
            model.addAttribute("route", newRoute);
            model.addAttribute("isEditMode", false);
            model.addAttribute("allBusStations", busStationService.findAll().values());
        }

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