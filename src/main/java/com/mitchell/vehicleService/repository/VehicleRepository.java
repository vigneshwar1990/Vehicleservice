package com.mitchell.vehicleService.repository;

import com.mitchell.vehicleService.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    List<Vehicle> findByYear(int year);
    List<Vehicle> findByMakeAndModel(String make, String model);
}