package taxiservice.dao;

import java.util.List;
import java.util.Optional;
import taxiservice.db.Storage;
import taxiservice.lib.DaoImpl;
import taxiservice.models.Manufacture;

@DaoImpl
public class ManufactureDaoImpl implements ManufactureDao {
    @Override
    public Manufacture create(Manufacture element) {
        return Storage.addManufactureToStorageAndTakeId(element);
    }

    @Override
    public Optional<Manufacture> get(Long id) {
        return Storage.getManufactureStorage()
                .stream()
                .filter(x -> x.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Manufacture> getAll() {
        return Storage.getManufactureStorage();
    }

    @Override
    public Manufacture update(Manufacture element) {
        Storage.getManufactureStorage()
                .stream()
                .filter(x -> x.getId().equals(element.getId()))
                .findFirst()
                .ifPresent(x -> Storage.getManufactureStorage()
                        .set(element.getId().intValue(), element));
        return element;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.getManufactureStorage()
                .removeIf(x -> x.getId().equals(id));
    }
}
