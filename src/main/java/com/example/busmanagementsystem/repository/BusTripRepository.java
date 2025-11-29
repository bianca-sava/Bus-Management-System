package com.example.busmanagementsystem.repository;
import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.model.BusTrip;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public class BusTripRepository extends InFileRepository<BusTrip> {

    protected BusTripRepository(ObjectMapper objectMapper, @Value("${repository.filepath.bustrip}") String filePath ) {
        super(filePath, objectMapper,  BusTrip.class);
    }

    @Override
    protected String getIdFromEntity(BusTrip entity) {
        return entity.getId();
    }
}
