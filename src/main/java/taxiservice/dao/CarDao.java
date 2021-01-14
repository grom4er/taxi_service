package taxiservice.dao;

import java.util.List;

import taxiservice.models.Car;

public interface CarDao extends GenericDao<Car> {
    List<Car> getAllByDriver(Long driverId);
}
