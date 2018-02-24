package com.mitchell.vehicleService.controller;

import com.mitchell.vehicleService.entity.Vehicle;
import com.mitchell.vehicleService.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@Slf4j
@RequestMapping("/vehicles")
public final class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Vehicle> retrieveVehicle(@PathVariable("id") Integer id) {
        log.info("Vehicle Controller: Retrieve Vehicle Based on Id");
        return ok(vehicleService.retrieveVehicleById(id));
    }

    @GetMapping(value = "year/{year}")
    public ResponseEntity<List<Vehicle>> retrieveVehicleByYear(@PathVariable("year") Integer year) {
        log.info("Vehicle Controller: Retrieve Vehicle Based on Year");
        return ok(vehicleService.retrieveVehiclesByYear(year));
    }

    @GetMapping(value = "make/{make}/model/{model}")
    public ResponseEntity<List<Vehicle>> retrieveVehicleByMakeAndModel(@PathVariable("make") String make, @PathVariable("model") String model) {
        log.info("Vehicle Controller: Retrieve Vehicle Based on Make and Model");
        return ok(vehicleService.retrieveVehicleByMakeAndModel(make, model));
    }

    @GetMapping()
    public ResponseEntity <List<Vehicle>> retrieveVehicles() {
        log.info("Vehicle Controller: Retrieve All Vehicles");
        return ok(vehicleService.retrieveVehicles());
    }

    @PostMapping()
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle){
        log.info("Vehicle Controller: Vehicle creation ");
        ResponseEntity.BodyBuilder builder = status(HttpStatus.CREATED);
        return builder.body(vehicleService.createVehicle(vehicle));
       }

    @PutMapping()
    public ResponseEntity<Vehicle> updateVehicle(@RequestBody Vehicle vehicle){
        log.info("Vehicle Controller: Vehicle Update");
        return ok(vehicleService.updateVehicle(vehicle));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<String> deleteVehicle(@PathVariable("id") Integer id){
        log.info("Vehicle Controller: Vehicle Deletion");
        return ok(vehicleService.deleteVehicle(id));
    }
}