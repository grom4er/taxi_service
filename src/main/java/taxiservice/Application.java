package taxiservice;

import taxiservice.lib.Injector;
import taxiservice.models.Car;
import taxiservice.models.Driver;
import taxiservice.models.Manufacture;
import taxiservice.service.ManufactureService;

public class Application {
    private static final Injector injector =
            Injector.getInstance(Application.class.getPackageName());

    public static void main(String[] args) {
        ManufactureService manufacturerService =
                (ManufactureService) injector.getInstance(ManufactureService.class);
        Manufacture bmwManufacture = new Manufacture("BMW", "Germany");
        Manufacture mazdaManufacture = new Manufacture("Mazda", "Japan");
        Car bmw = new Car("M5", bmwManufacture);
        Driver vasya = new Driver("Vasya", "DAO3");
        bmw.getDrivers().add(vasya);
        manufacturerService.create(bmwManufacture);
        manufacturerService.create(mazdaManufacture);
        System.out.println("Show all manufacture in storage "
                + manufacturerService.getAll());
        System.out.println("Get first manufacture " + manufacturerService.get(1L));
        System.out.println("Delete first manufacture" + manufacturerService.delete(1L));
        System.out.println("Show all manufactures after delete one" + manufacturerService.getAll());
    }
}
