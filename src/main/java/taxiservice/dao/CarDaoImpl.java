package taxiservice.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import taxiservice.db.Storage;
import taxiservice.models.Car;
import taxiservice.models.Driver;

public class CarDaoImpl implements CarDao {

    @Override
    public Car create(Car element) {
        return Storage.addCarToStorageAndTakeId(element);
    }

    @Override
    public Optional<Car> get(Long id) {
        return Storage.getCarsStorage()
                .stream()
                .filter(x -> x.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Car> getAll() {
        return Storage.getCarsStorage();
    }

    @Override
    public Car update(Car element) {
        IntStream.range(0, Storage.getCarsStorage().size())
                .filter(i -> Storage.getCarsStorage()
                        .get(i).getId().equals(element.getId()))
                .findFirst()
                .ifPresent(i -> Storage.getCarsStorage().set(i, element));
        return element;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.getCarsStorage()
                .removeIf(x -> x.getId().equals(id));
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        List<Car> carWithDrivers = new ArrayList<>();
        for (Car car : Storage.getCarsStorage()) {
            for (Driver driver : car.getDrivers()) {
                if (driver.getId().equals(driverId)) {
                    carWithDrivers.add(car);
                }
            }
        }
        return carWithDrivers;
    }
}
