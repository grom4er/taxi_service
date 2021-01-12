package taxi_service.db;

import taxi_service.models.Manufacture;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static List<Manufacture> manufactureStorage = new ArrayList<>();
    private static long manufactureId = 0;

    public static Manufacture addManufactureToStorageAndTakeId(Manufacture manufacturer) {
        manufacturer.setId(++manufactureId);
        manufactureStorage.add(manufacturer);
        return manufacturer;
    }

    public static List<Manufacture> getManufactureStorage() {
        return manufactureStorage;
    }
}
