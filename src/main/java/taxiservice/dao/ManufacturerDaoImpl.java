package taxiservice.dao;

import java.util.List;
import java.util.Optional;
import taxiservice.db.Storage;
import taxiservice.lib.DaoImpl;
import taxiservice.models.Manufacturer;

@DaoImpl
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
        Storage.getManufacturerStorage()
                .stream()
                .filter(x -> x.getId().equals(element.getId()))
                .findFirst()
                .ifPresent(x -> Storage.getManufacturerStorage()
                        .set(element.getId().intValue(), element));
        return element;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.getManufacturerStorage()
                .removeIf(x -> x.getId().equals(id));
    }
}
