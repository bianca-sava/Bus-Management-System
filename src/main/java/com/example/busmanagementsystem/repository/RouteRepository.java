package com.example.busmanagementsystem.repository;
import com.example.busmanagementsystem.model.Route;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RouteRepository implements CRUD<Route> {

    private List<Route> routes;

    public RouteRepository() {
        routes = new ArrayList<Route>();
    }

    @Override
    public boolean create(Route entity) {
        return routes.add(entity);
    }

    @Override
    public List findAll() {
        return routes;
    }

    @Override
    public Route findById(String id) {
        for (Route route : routes) {
            if (route.getId().equals(id)) {
                return route;
            }
        }
        return null;
    }

    @Override
    public boolean update(String id, Route entity) {
        for (int i = 0; i < routes.size(); i++) {
            if (routes.get(i).getId().equals(id)) {
                routes.set(i, (Route) entity);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        return routes.removeIf(route -> route.getId().equals(id));
    }
}
