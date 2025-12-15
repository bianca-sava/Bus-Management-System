package com.example.busmanagementsystem.service.databaseServices;

import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.model.Driver;
import com.example.busmanagementsystem.repository.interfaces.DriverJpaRepository;
import com.example.busmanagementsystem.service.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DriverDatabaseService implements Validate<Driver> {
    private final DriverJpaRepository driverRepository;

    @Autowired
    public DriverDatabaseService(DriverJpaRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public void validate(Driver driver) throws RuntimeException {
        if(driverRepository.existsById(driver.getId().trim()))
            throw new DuplicateAttributeException("id", "The id of the driver must be unique. " +
                    "The id: " +  driver.getId() + " already exists.");
    }

    public boolean addDriver(Driver driver){
        validate(driver);

        return driverRepository.save(driver) != null;
    }

    public Driver getDriverById(String id){
        return driverRepository.findById(id).orElse(null);
    }

    public Page<Driver> findAllDriversPageable(
            String id,
            String name,
            String minYears,
            String maxYears,
            Pageable pageable) {

        Sort.Order assignmentCountOrder = pageable.getSort().getOrderFor("nrOfAssignments");

        if (assignmentCountOrder != null) {
            String sortAlias = "assignmentCount";
            Sort newSort = Sort.by(assignmentCountOrder.getDirection(), sortAlias);
            Pageable customPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), newSort);

            Page<Object[]> result = driverRepository.findAllSortedByAssignmentCount(customPageable);

            List<Driver> content = result.getContent().stream()
                    .map(obj -> (Driver) obj[0])
                    .collect(Collectors.toList());

            return new PageImpl<>(content, pageable, result.getTotalElements());

        } else {
            return driverRepository.findByFilters(id, name, minYears, maxYears, pageable);
        }
    }

    public Map<String, Driver> findAllDrivers(){
        return driverRepository.findAll().stream()
                .collect(Collectors.toMap(Driver::getId,
                        driver -> driver));
    }

    public boolean updateDriver(String id, Driver driver){
        Optional<Driver> driverOptional = driverRepository.findById(id);
        if(driverOptional.isPresent()){
            Driver existingDriver = driverOptional.get();

            existingDriver.setId(driver.getId());
            existingDriver.setName(driver.getName());
            existingDriver.setAssignments(driver.getAssignments());
            existingDriver.setYearsOfExperience(driver.getYearsOfExperience());

            driverRepository.save(existingDriver);
            return true;
        }
        return false;
    }

    public boolean deleteDriver(String id){
        if(driverRepository.existsById(id)){
            driverRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
