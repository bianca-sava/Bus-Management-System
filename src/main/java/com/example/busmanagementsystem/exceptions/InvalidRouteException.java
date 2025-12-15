package com.example.busmanagementsystem.exceptions;

import com.example.busmanagementsystem.model.Route;

public class InvalidRouteException extends RuntimeException {
    public InvalidRouteException(Route route) {
        super("The origin and destination of the route can't be the same. Current origin is: "
                + route.getOrigin() + " and current destination is: " + route.getDestination());
    }
}
