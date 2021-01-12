package taxi_service.models;

import java.util.List;

public class Car {
    private Long id;
    private String model;
    private Manufacture manufacture;
    private List<Driver> drivers;
}