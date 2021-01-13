package taxiservice.dao;

import taxiservice.models.Car;

import java.util.List;
import java.util.Optional;

public class CarDaoImpl implements CarDao{
    @Override
    public List<Car> getAllByDriver(Long driverId) {
        return null;
    }

    @Override
    public Car create(Car element) {
        return null;
    }

    @Override
    public Optional<Car> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Car> getAll() {
        return null;
    }

    @Override
    public Car update(Car element) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
