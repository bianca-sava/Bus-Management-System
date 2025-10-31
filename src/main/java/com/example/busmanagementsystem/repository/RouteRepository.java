package com.example.busmanagementsystem.repository;
import com.example.busmanagementsystem.model.Route;
import org.springframework.stereotype.Repository;

@Repository
public class RouteRepository extends InMemoryRepository<Route> {

    @Override
    protected String getIdFromEntity(Route entity) {
        return entity.getId();
    }
}
