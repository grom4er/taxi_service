package taxiservice.db;

import java.util.ArrayList;
import java.util.List;
import taxiservice.models.Manufacture;

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
