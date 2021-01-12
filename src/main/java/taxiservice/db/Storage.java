package taxiservice.db;

import java.util.ArrayList;
import java.util.List;
import taxiservice.models.Manufacturer;

public class Storage {
    private static List<Manufacturer> manufacturerStorage = new ArrayList<>();
    private static long manufacturerId = 0;

    public static Manufacturer addManufactureToStorageAndTakeId(Manufacturer manufacturer) {
        manufacturer.setId(++manufacturerId);
        manufacturerStorage.add(manufacturer);
        return manufacturer;
    }

    public static List<Manufacturer> getManufacturerStorage() {
        return manufacturerStorage;
    }
}
