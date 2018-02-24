package com.mitchell.vehicleService.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Vehicle", schema = "Mitchell")
public class Vehicle {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonProperty("Id")
    private int id;
    @JsonProperty("Year")
    private int year;
    @JsonProperty("Make")
    private String make;
    @JsonProperty("Model")
    private String model;
}