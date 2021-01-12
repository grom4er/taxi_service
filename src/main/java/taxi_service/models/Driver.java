package taxi_service.models;

import java.util.List;

public class Driver {
    private Long id;
    private String name;
    private String licenseNumber;
    // keep in mind, one driver can drive several cars per day
    // but we do not wan't to store a list of cars in a Driver class
}