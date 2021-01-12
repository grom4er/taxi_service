package taxiservice;

import taxiservice.lib.Injector;
import taxiservice.models.Car;
import taxiservice.models.Driver;
import taxiservice.models.Manufacturer;
import taxiservice.service.ManufactureService;

public class Application {
    private static final Injector injector =
            Injector.getInstance(Application.class.getPackageName());

    public static void main(String[] args) {
        ManufactureService manufacturerService =
                (ManufactureService) injector.getInstance(ManufactureService.class);
        Manufacturer bmwManufacturer = new Manufacturer("BMW", "Germany");
        Manufacturer mazdaManufacturer = new Manufacturer("Mazda", "Japan");
        Car bmw = new Car("M5", bmwManufacturer);
        Driver vasya = new Driver("Vasya", "DAO3");
        bmw.getDrivers().add(vasya);
        manufacturerService.create(bmwManufacturer);
        manufacturerService.create(mazdaManufacturer);
        System.out.println("Show all manufacture in storage "
                + manufacturerService.getAll());
        System.out.println("Get first manufacture " + manufacturerService.get(1L));
        System.out.println("Delete first manufacture" + manufacturerService.delete(1L));
        System.out.println("Show all manufactures after delete one" + manufacturerService.getAll());
    }
}
