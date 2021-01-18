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
    private static final ManufactureService manufacturerService =
            (ManufactureService) injector.getInstance(ManufactureService.class);
    private static final CarService carService =
            (CarService) injector.getInstance(CarService.class);
    private static final DriverService driverService =
            (DriverService) injector.getInstance(DriverService.class);
    private static final Manufacturer bmwManufacturer = new Manufacturer("BMW", "Germany");
    private static final Manufacturer mazdaManufacturer = new Manufacturer("Mazda", "Japan");
    private static final Car bmw = new Car("M5", bmwManufacturer);
    private static final Car mazda = new Car("R5", mazdaManufacturer);
    private static final Driver kolya = new Driver("Kolya", "DEDINS5");
    private static final Driver vasya = new Driver("Vasya", "DAO3");

    public static void main(String[] args) {
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
        one = service.create(one);
        two = service.create(two);
        System.out.printf(SHOW_ALL_IN_STORAGE + service.getAll(), manufactureText);
        System.out.printf("\n" + GET_FIRST_FROM_STORAGE + service.get(one.getId()), manufactureText);
        System.out.printf("\n" + DELETE_FIRST_FROM_STORAGE + service.delete(one.getId()), manufactureText);
        System.out.printf("\n" + SHOW_ALL_STORAGE_AFTER_DELETE + service.getAll(), manufactureText);
    }

    public static void testCarService(CarService service, Car firstCar, Car secondCar,
                                      Driver driver, DriverService driverService) {

        firstCar = service.create(firstCar);
        secondCar = service.create(secondCar);
        driver = driverService.create(driver);
        String carText = "Cars";
        System.out.printf(SHOW_ALL_IN_STORAGE + service.getAll(), carText);
        System.out.printf("\n" + GET_FIRST_FROM_STORAGE + service.get(firstCar.getId()), carText);
        System.out.printf("\n" + DELETE_FIRST_FROM_STORAGE + service.delete(firstCar.getId()), carText);
        service.addDriverToCar(driver, secondCar);
        System.out.println("\n" + "Add new driver to first car");
        System.out.printf(SHOW_ALL_IN_STORAGE + service.getAll(), carText);
        service.removeDriverFromCar(driver, secondCar);
        System.out.println("\n" + "remove driver from first car");
        System.out.printf(SHOW_ALL_IN_STORAGE + service.getAll(), carText);
        service.addDriverToCar(driver, secondCar);
        System.out.println("\n" + "get car by id 1 driver" + service.getAllByDriver(driver.getId()));
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
