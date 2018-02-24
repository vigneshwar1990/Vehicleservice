package com.mitchell.vehicleService.service;

import com.mitchell.vehicleService.config.TestSuiteConfig;
import com.mitchell.vehicleService.entity.Vehicle;
import com.mitchell.vehicleService.repository.VehicleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestSuiteConfig.class }, initializers = ConfigFileApplicationContextInitializer.class)
public class VehicleServiceTest {

    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

   private List<Vehicle> vehicleList;

    private Vehicle defaultVehicle;

    @Before
    public void init() {
        vehicleList = new ArrayList<>();
        vehicleService = new VehicleService(vehicleRepository);

        defaultVehicle= new Vehicle();
        defaultVehicle.setId(1);
        defaultVehicle.setMake("Toyota");
        defaultVehicle.setYear(1995);
        defaultVehicle.setModel("Camry");

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(2);
        vehicle1.setMake("Honda");
        vehicle1.setYear(1995);
        vehicle1.setModel("Civic");
        vehicleList.add(defaultVehicle);
        vehicleList.add(vehicle1);
    }
    @Test
    public void retrieveVehicleById() throws Exception {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1);
        vehicle.setMake("Toyota");
        vehicle.setYear(1995);
        vehicle.setModel("Camry");
        when(vehicleRepository.findOne(1)).thenReturn(vehicle);
        Vehicle result = vehicleService.retrieveVehicleById(1);
        assertThat(result.getId(),is(1));
    }

    @Test
    public void retrieveVehiclesByYear() throws Exception {
        when(vehicleRepository.findByYear(1995)).thenReturn(vehicleList);
        List<Vehicle> result = vehicleService.retrieveVehiclesByYear(1995);
        assertThat(result.get(0).getYear(),is(1995));
    }

    @Test
    public void retrieveVehicles() throws Exception {
        when(vehicleRepository.findAll()).thenReturn(vehicleList);
        List<Vehicle> result = vehicleService.retrieveVehicles();
        assertThat(result.size(),is(2));
    }

    @Test
    public void createVehicle() throws Exception {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1);
        vehicle.setMake("Toyota");
        vehicle.setYear(1995);
        vehicle.setModel("Camry");
         when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
         Vehicle result = vehicleService.createVehicle(defaultVehicle);
        assertThat(result.getId(),is(1));
    }

    @Test
    public void updateVehicle() throws Exception {
        defaultVehicle.setYear(2020);
        when(vehicleRepository.findOne(1)).thenReturn(defaultVehicle);
         when(vehicleRepository.save(defaultVehicle)).thenReturn(defaultVehicle);
        Vehicle result = vehicleService.updateVehicle(defaultVehicle);
        assertThat(result.getYear(),is(2020));
    }

    @Test
    public void deleteVehicle() throws Exception {
       vehicleService.deleteVehicle(1);
        verify(vehicleRepository, times(1)).delete(1);
    }

    @Test
    public void retrieveVehicleByMakeAndModel() throws Exception {
        when(vehicleRepository.findByMakeAndModel(anyString(),anyString())).thenReturn(vehicleList);
         List<Vehicle> result = vehicleService.retrieveVehicleByMakeAndModel("toyota","camry");
        assertThat(result.size(),is(2));
    }

}