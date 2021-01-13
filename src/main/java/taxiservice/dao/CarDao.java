package taxiservice.dao;

import taxiservice.models.Car;

import java.util.List;

public interface CarDao extends GenericDao<Car>{
    List<Car> getAllByDriver(Long driverId);
}
