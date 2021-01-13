package taxiservice.db;

import java.util.ArrayList;
import java.util.List;

import taxiservice.models.Car;
import taxiservice.models.Driver;
import taxiservice.models.Manufacturer;

public class Storage {
    private static List<Manufacturer> manufacturerStorage = new ArrayList<>();
    private static long manufacturerId = 0;
    private static List<Car> carsStorage = new ArrayList<>();
    private static long carId = 1;
    private static List<Driver> driversStorage = new ArrayList<>();
    private static long driverId = 0;

    public static Manufacturer addManufactureToStorageAndTakeId(Manufacturer manufacturer) {
        manufacturer.setId(++manufacturerId);
        manufacturerStorage.add(manufacturer);
        return manufacturer;
    }

    public static Car addCarToStorageAndTakeId(Car car) {
        car.setId(++carId);
        carsStorage.add(car);
        return car;
    }

    public static Driver addDriverToStorageAndTakeId(Driver driver) {
        driver.setId(++driverId);
        driversStorage.add(driver);
        return driver;
    }

    public static List<Manufacturer> getManufacturerStorage() {
        return manufacturerStorage;
    }

    public static List<Car> getCarsStorage() {
        return carsStorage;
    }

    public static List<Driver> getDriversStorage() {
        return driversStorage;
    }
}
