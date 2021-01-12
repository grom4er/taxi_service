package taxi_service.dao;

import taxi_service.db.Storage;
import taxi_service.models.Manufacture;

import java.util.List;
import java.util.Optional;

public class ManufactureDaoImpl implements Dao<Manufacture> {
    @Override
    public Manufacture create(Manufacture element) {
        return Storage.addManufactureToStorageAndTakeId(element);
    }

    @Override
    public Optional<Manufacture> get(long id) {
        if (checkValidId(id)) {
            return Optional.empty();
        }
        return Optional.of(Storage.getManufactureStorage().get((int) (id - 1)));
    }

    @Override
    public List<Manufacture> getAll() {
        return Storage.getManufactureStorage();
    }

    @Override
    public Manufacture update(Manufacture element) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        if (checkValidId(id)) {
            return false;
        }
        return Storage.getManufactureStorage().remove(id);
    }

    private boolean checkValidId(long id) {
        return id <= 0 || id > Storage.getManufactureStorage().size();
    }
}
