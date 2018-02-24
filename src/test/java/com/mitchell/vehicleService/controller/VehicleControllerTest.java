package com.mitchell.vehicleService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitchell.vehicleService.entity.Vehicle;
import com.mitchell.vehicleService.repository.VehicleRepository;
import com.mitchell.vehicleService.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@Slf4j
public class VehicleControllerTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    VehicleService vehicleService;

    @MockBean
    VehicleRepository vehicleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    private RestDocumentationResultHandler document;
    private MockMvc mockMvc;

    private List<Vehicle> vehicleList;


    @Before
    public void setUp() throws Exception {
        this.document = document("{method-name}");
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(this.restDocumentation).uris().withScheme("https"))
                .alwaysDo(this.document).build();

        vehicleList = new ArrayList<>();

        Vehicle defaultVehicle= new Vehicle();
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
    public void retrieveVehicle() throws Exception {

        Vehicle Vehicle = new Vehicle();
        Vehicle.setId(2);
        Vehicle.setMake("Toyota");
        Vehicle.setYear(1995);
        Vehicle.setModel("Camry");

        when(vehicleService.retrieveVehicleById(2)).thenReturn(Vehicle);

        try {
            this.mockMvc
                    .perform(get("/vehicles/{id}",2)
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk())
                            .andDo(document("retrieve-vehicle",
                                    pathParameters(
                                            parameterWithName("id").description("Vehicle Unique Id")),
                                    responseFields(retrieveVehicleResponse())
                                    ));
        } catch (Exception e) {
            log.error("Error occurred due to : "+e);
        }

    }

    @Test
    public void retrieveVehicles() throws Exception {

        Vehicle Vehicle = new Vehicle();
        Vehicle.setId(2);
        Vehicle.setMake("Toyota");
        Vehicle.setYear(1995);
        Vehicle.setModel("Camry");

        when(vehicleService.retrieveVehicles()).thenReturn(vehicleList);

        try {
            this.mockMvc
                    .perform(get("/vehicles/",1)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(document("retrieve-all-vehicles",
                            responseFields(retrieveVehicleResponse())
                    ));
        } catch (Exception e) {
            log.error("Error occurred due to : "+e);
        }
    }

    @Test
    public void createVehicle() throws Exception {
        Vehicle Vehicle = new Vehicle();
        Vehicle.setId(2);
        Vehicle.setMake("Toyota");
        Vehicle.setYear(1995);
        Vehicle.setModel("Camry");

        when(vehicleService.createVehicle(Vehicle)).thenReturn(Vehicle);

        try {
            this.mockMvc
                    .perform(post("/vehicles/",1)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(Vehicle))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andDo(document("create-vehicle",
                             requestFields(retrieveVehicleResponse()),
                             responseFields(retrieveVehicleResponse())));
        } catch (Exception e) {
            log.error("Error occurred due to : "+e);
        }
    }

    @Test
    public void updateVehicle() throws Exception {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(2);
        vehicle.setMake("Toyota");
        vehicle.setYear(1995);
        vehicle.setModel("Camry");

        when(vehicleService.updateVehicle(vehicle)).thenReturn(vehicle);

        try {
            this.mockMvc
                    .perform(put("/vehicles/",1)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(vehicle))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(document("update-vehicle",
                            requestFields(retrieveVehicleResponse()),
                            responseFields(retrieveVehicleResponse())));
        } catch (Exception e) {
            log.error("Error occurred due to : "+e);
        }
    }

    @Test
    public void deleteVehicle() throws Exception {

        Vehicle Vehicle = new Vehicle();
        Vehicle.setId(2);
        Vehicle.setMake("Toyota");
        Vehicle.setYear(1995);
        Vehicle.setModel("Camry");

        when(vehicleService.deleteVehicle(1)).thenReturn("");

        try {
            this.mockMvc
                    .perform(delete("/vehicles/{id}",2)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(document("delete-vehicle",
                            pathParameters(
                                    parameterWithName("id").description("Vehicle Unique Id")),
                            responseFields(retrieveVehicleResponse())
                    ));
        } catch (Exception e) {
            log.error("Error occurred due to : "+e);
        }
    }

    @Test
    public void retrieveVehiclesByYear() throws Exception {

        when(vehicleService.retrieveVehiclesByYear(1995)).thenReturn(vehicleList);

        try {
            this.mockMvc
                    .perform(get("/vehicles/year/{year}",1995)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(document("retrieve-all-vehicles-year",                                    pathParameters(parameterWithName("year").description("Year")),
                            responseFields(retrieveVehicleResponse())
                    ));
        } catch (Exception e) {
            log.error("Error occurred due to : "+e);
        }
    }

    @Test
    public void retrieveVehiclesByMakeAndModel() throws Exception {

        when(vehicleService.retrieveVehicleByMakeAndModel("Toyota","Camry")).thenReturn(vehicleList);

        try {
            this.mockMvc
                    .perform(get("/vehicles/make/{make}/model/{model}/","Toyota","Camry")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(document("retrieve-all-vehicles-makeandmodel",
                            pathParameters(
                                    parameterWithName("make").description("Vehicle Make"),
                                    parameterWithName("model").description("Vehicle Model")),
                            responseFields(retrieveVehicleResponse())
                    ));
        } catch (Exception e) {
            log.error("Error occurred due to : "+e);
        }
    }

    private FieldDescriptor[] retrieveVehicleResponse() {
        return new FieldDescriptor[] {
                fieldWithPath("Id").description("Vehicle Unique Id"),
                fieldWithPath("Year").description("Year"),
                fieldWithPath("Make").description("Make"),
                fieldWithPath("Model").description("Model")
            };
    }
}