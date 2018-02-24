package com.mitchell.vehicleService.service;

import com.mitchell.vehicleService.constants.VehicleConstants;
import com.mitchell.vehicleService.entity.Vehicle;
import com.mitchell.vehicleService.exception.MitchellValidationException;
import com.mitchell.vehicleService.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mitchell.vehicleService.constants.VehicleConstants.SUCCESS_MSG;
import static org.springframework.util.StringUtils.isEmpty;

@Service
@Slf4j
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle retrieveVehicleById(Integer id) {
        return vehicleRepository.findOne(id);
    }

    public List<Vehicle> retrieveVehiclesByYear(int year) {
        return vehicleRepository.findByYear(year);
    }

     public List<Vehicle> retrieveVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle createVehicle(Vehicle vehicle){
        if(validateVehicle(vehicle)) {
            return vehicleRepository.save(vehicle);
        }else {
            throw new MitchellValidationException("Please Make Sure Non-Empty make and model is specified and the year must be between 1950 and 2050");
        }
    }

    public Vehicle updateVehicle(Vehicle vehicle) {
        Vehicle oldVehicle = vehicleRepository.findOne(vehicle.getId());
        if(oldVehicle!=null){
            return vehicleRepository.save(vehicle);
        }else{
            throw new MitchellValidationException("No Vehicle Found for Update");
        }
    }

    private boolean validateVehicle(Vehicle vehicle){
        return !isEmpty(vehicle.getMake()) && !isEmpty(vehicle.getModel()) && (vehicle.getYear() > VehicleConstants.YEAR1) && (vehicle.getYear() < VehicleConstants.YEAR2);
    }

    public String deleteVehicle(Integer id) {
         try{
             vehicleRepository.delete(id);
         }catch (Exception e){
             log.info("Error in Deleting Vehicle Id-"+id);
             log.error(e.getMessage(),e);
             throw new RuntimeException(e.getMessage(),e);
         }
         return SUCCESS_MSG;
    }

    public List<Vehicle> retrieveVehicleByMakeAndModel(String make, String model) {
        return vehicleRepository.findByMakeAndModel(make,model);
    }
}