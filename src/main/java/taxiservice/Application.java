package taxiservice;

import taxiservice.lib.Injector;
import taxiservice.models.Car;
import taxiservice.models.Driver;
import taxiservice.models.Manufacturer;
import taxiservice.service.CarService;
import taxiservice.service.DriverService;
import taxiservice.service.ManufactureService;

public class Application {
    private static final Injector injector =
            Injector.getInstance(Application.class.getPackageName());
    private static final String SHOW_ALL_IN_STORAGE = "Show all %s in storage: ";
    private static final String GET_FIRST_FROM_STORAGE = "Get first %s from storage: ";
    private static final String DELETE_FIRST_FROM_STORAGE = "Delete first %s from storage ";
    private static final String SHOW_ALL_STORAGE_AFTER_DELETE =
            "Show all %s after delete one from storage ";

    public static void main(String[] args) {
        ManufactureService manufacturerService =
                (ManufactureService) injector.getInstance(ManufactureService.class);
        CarService carService = (CarService) injector.getInstance(CarService.class);
        DriverService driverService = (DriverService) injector.getInstance(DriverService.class);
        Manufacturer bmwManufacturer = new Manufacturer("BMW", "Germany");
        Manufacturer mazdaManufacturer = new Manufacturer("Mazda", "Japan");
        Car bmw = new Car("M5", bmwManufacturer);
        Car mazda = new Car("R5", mazdaManufacturer);
        Driver kolya = new Driver("Kolya", "DEDINS5");
        Driver vasya = new Driver("Vasya", "DAO3");

        System.err.println("Test Manufacture service");
        testManufactureService(manufacturerService, bmwManufacturer, mazdaManufacturer);

        System.err.println("\nTest Car service");
        testCarService(carService, bmw, mazda, kolya, driverService);

        System.err.println("\nTest Drive service");
        testDriveService(driverService, vasya);
    }

    public static void testManufactureService(ManufactureService service,
                                              Manufacturer one, Manufacturer two) {
        String manufactureText = "Manufacture";
        service.create(one);
        service.create(two);
        System.out.printf(SHOW_ALL_IN_STORAGE + service.getAll(), manufactureText);
        System.out.printf("\n" + GET_FIRST_FROM_STORAGE + service.get(1L), manufactureText);
        System.out.printf("\n" + DELETE_FIRST_FROM_STORAGE + service.delete(1L), manufactureText);
        System.out.printf("\n" + SHOW_ALL_STORAGE_AFTER_DELETE + service.getAll(), manufactureText);
    }

    public static void testCarService(CarService service, Car firstCar, Car secondCar,
                                      Driver driver, DriverService driverService) {
        String carText = "Cars";
        service.create(firstCar);
        service.create(secondCar);
        driverService.create(driver);
        System.out.printf(SHOW_ALL_IN_STORAGE + service.getAll(), carText);
        System.out.printf("\n" + GET_FIRST_FROM_STORAGE + service.get(1L), carText);
        System.out.printf("\n" + DELETE_FIRST_FROM_STORAGE + service.delete(1L), carText);
        service.addDriverToCar(driver, secondCar);
        System.out.println("\n" + "Add new driver to first car");
        System.out.printf(SHOW_ALL_IN_STORAGE + service.getAll(), carText);
        service.removeDriverFromCar(driver, secondCar);
        System.out.println("\n" + "remove driver from first car");
        System.out.printf(SHOW_ALL_IN_STORAGE + service.getAll(), carText);
        service.addDriverToCar(driver, secondCar);
        System.out.println("\n" + "get car by id 1 driver" + service.getAllByDriver(1L));
    }

    public static void testDriveService(DriverService service, Driver two) {
        service.create(two);
        String driverText = "Drivers";
        System.out.printf(SHOW_ALL_IN_STORAGE + service.getAll(), driverText);
        System.out.printf("\n" + GET_FIRST_FROM_STORAGE + service.get(2L), driverText);
        System.out.printf("\n" + DELETE_FIRST_FROM_STORAGE + service.delete(2L), driverText);
        System.out.printf("\n" + SHOW_ALL_STORAGE_AFTER_DELETE + service.getAll(), driverText);
    }
}
