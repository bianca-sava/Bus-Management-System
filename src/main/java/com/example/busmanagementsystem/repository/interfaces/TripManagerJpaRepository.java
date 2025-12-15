package com.example.busmanagementsystem.repository.interfaces;
import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.model.TripManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TripManagerJpaRepository extends JpaRepository<TripManager, String> {
    boolean existsByEmployeeCode(String employeeCode);

    @Query("SELECT tm FROM TripManager tm WHERE " +
            "(:id IS NULL OR LOWER(tm.id) LIKE LOWER(CONCAT('%', :id, '%'))) AND " +
            "(:name IS NULL OR LOWER(tm.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:code IS NULL OR LOWER(tm.employeeCode) LIKE LOWER(CONCAT('%', :code, '%'))) AND " +
            "(:minAssign IS NULL OR SIZE(tm.assignments) >= :minAssign) AND " +
            "(:maxAssign IS NULL OR SIZE(tm.assignments) <= :maxAssign)")
    Page<TripManager> findByFilters(
            @Param("id") String id,
            @Param("name") String name,
            @Param("code") String employeeCode,
            @Param("minAssign") Integer minAssignments,
            @Param("maxAssign") Integer maxAssignments,
            Pageable pageable
    );
}