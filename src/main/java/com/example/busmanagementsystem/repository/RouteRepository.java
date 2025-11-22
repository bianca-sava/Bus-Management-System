package com.example.busmanagementsystem.repository;
import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.model.Route;
import com.example.busmanagementsystem.repository.interfaces.RouteJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class RouteRepository extends DatabaseRepository<Route> {

    public RouteRepository(RouteJpaRepository jpaRepository) {
        super(jpaRepository);
    }

//    protected RouteRepository(ObjectMapper objectMapper, @Value("${repository.filepath.route}") String filePath ) {
//        super(filePath, objectMapper,  Route.class);
//    }

    @Override
    protected String getIdFromEntity(Route entity) {
        return entity.getId();
    }
}
