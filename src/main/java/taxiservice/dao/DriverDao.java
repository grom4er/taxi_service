package taxiservice.dao;

import java.util.Optional;
import taxiservice.models.Driver;

public interface DriverDao extends GenericDao<Driver, Long> {
    Optional<Driver> findByLogin(String login);
}
