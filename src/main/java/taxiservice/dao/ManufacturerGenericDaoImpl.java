package taxiservice.dao;

import java.util.List;
import java.util.Optional;
import taxiservice.db.Storage;
import taxiservice.lib.Dao;
import taxiservice.models.Manufacturer;

@Dao
public class ManufacturerGenericDaoImpl implements ManufacturerGenericDao {
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
                        .set(element.getId().intValue()-1, element));
        return element;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.getManufacturerStorage()
                .removeIf(x -> x.getId().equals(id));
    }
}
