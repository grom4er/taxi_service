package taxiservice.dao.jbdc;

import taxiservice.dao.DriverDao;
import taxiservice.models.Driver;

import java.util.List;
import java.util.Optional;

public class DriverJdbcDaoIml implements DriverDao {
    @Override
    public Driver create(Driver element) {
        return null;
    }

    @Override
    public Optional<Driver> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Driver> getAll() {
        return null;
    }

    @Override
    public Driver update(Driver element) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
