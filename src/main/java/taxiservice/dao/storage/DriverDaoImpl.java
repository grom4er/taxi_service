package taxiservice.dao.storage;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import taxiservice.dao.DriverDao;
import taxiservice.db.Storage;
import taxiservice.lib.Dao;
import taxiservice.models.Driver;

@Dao
public class DriverDaoImpl implements DriverDao {
    @Override
    public Driver create(Driver element) {
        return Storage.addDriverToStorageAndTakeId(element);
    }

    @Override
    public Optional<Driver> get(Long id) {
        return Storage.getDriversStorage()
                .stream()
                .filter(x -> x.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Driver> getAll() {
        return Storage.getDriversStorage();
    }

    @Override
    public Driver update(Driver element) {
        IntStream.range(0, Storage.getDriversStorage().size())
                .filter(i -> Storage.getDriversStorage()
                        .get(i).getId().equals(element.getId()))
                .findFirst()
                .ifPresent(i -> Storage.getDriversStorage().set(i, element));
        return element;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.getDriversStorage()
                .removeIf(x -> x.getId().equals(id));
    }
}
