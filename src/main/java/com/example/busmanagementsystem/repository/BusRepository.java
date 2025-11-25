package com.example.busmanagementsystem.repository;
import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.repository.interfaces.BusJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public class BusRepository extends  DatabaseRepository<Bus>{

    protected BusRepository(BusJpaRepository jpaRepository) {
        super(jpaRepository);
    }

//    protected BusRepository(ObjectMapper objectMapper, @Value("${repository.filepath.bus}") String filePath ) {
//        super(filePath, objectMapper,  Bus.class);
//    }


    @Override
    protected String getIdFromEntity(Bus entity) {
        return entity.getId();
    }

}