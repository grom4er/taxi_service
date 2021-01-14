package taxiservice.dao.storage;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import taxiservice.dao.ManufacturerDao;
import taxiservice.db.Storage;
import taxiservice.lib.Dao;
import taxiservice.models.Manufacturer;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer element) {
        return Storage.addManufactureToStorageAndTakeId(element);
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        return Storage.getManufacturerStorage()
                .stream()
                .filter(x -> x.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Manufacturer> getAll() {
        return Storage.getManufacturerStorage();
    }

    @Override
    public Manufacturer update(Manufacturer element) {
        IntStream.range(0, Storage.getManufacturerStorage().size())
                .filter(i -> Storage.getManufacturerStorage()
                        .get(i).getId().equals(element.getId()))
                .findFirst()
                .ifPresent(i -> Storage.getManufacturerStorage().set(i, element));
        return element;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.getManufacturerStorage()
                .removeIf(x -> x.getId().equals(id));
    }
}
