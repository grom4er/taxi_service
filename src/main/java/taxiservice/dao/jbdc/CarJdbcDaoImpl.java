package taxiservice.dao.jbdc;

import taxiservice.dao.CarDao;
import taxiservice.exception.DataProcessingException;
import taxiservice.lib.Dao;
import taxiservice.models.Car;
import taxiservice.models.Driver;
import taxiservice.models.Manufacturer;
import taxiservice.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class CarJdbcDaoImpl implements CarDao {
    //ok
    @Override
    public Car create(Car element) {
        String query = "INSERT INTO cars (manufacturer_id, model) VALUE (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, element.getManufacturer().getId());
            statement.setString(2, element.getModel());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                element.setId(resultSet.getObject(1, Long.class));
            }
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    String.format("Data %s can't add to table", element), e);
        }
    }

    //ok
    @Override
    public List<Car> getAllByDriver(Long driverId) {
        String query = "SELECT c.id as id, model,  manufacturer_id, "
                + "m.name as manufacturer_name, "
                + "m.country as manufacturer_country "
                + "FROM cars c"
                + " LEFT JOIN manufacturers m on c.manufacturer_id = m.id"
                + " JOIN cars_drivers cd on c.id = cd.car_id"
                + " JOIN drivers d on cd.driver_id = d.id"
                + " WHERE c.deleted = false AND driver_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {
            preparedStatement.setLong(1, driverId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                cars.add(getCar(resultSet));
            }
            return cars;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all cars", e);
        }
    }

    //ok
    @Override
    public Optional<Car> get(Long id) {
        String query = "SELECT c.id as id, m.id as manufacture_id, model, "
                + "m.name as name, m.country as country FROM cars c"
                + "INNER JOIN manufacturesrs m ON m.id = c.id";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            return Optional.of(getCar(resultSet));
        } catch (SQLException e) {
            throw new DataProcessingException("Something going wrong"
                    + " with getting all drivers from car:" + id, e);
        }
    }

    @Override
    public List<Car> getAll() {
        String query = "SELECT c.id as id, "
                + "model, "
                + "manufacturer_id, "
                + "m.name as manufacturer_name, "
                + "m.country as manufacturer_country "
                + "FROM cars c"
                + " LEFT JOIN manufacturers m on c.manufacturer_id = m.id"
                + " where c.deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                cars.add(getCar(resultSet));
            }
            return cars;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all cars", e);
        }
    }

    @Override
    public Car update(Car element) {
        String selectQuery = "UPDATE cars SET model = ?, manufacturer_id = ? WHERE id = ?"
                + " and deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, element.getModel());
            preparedStatement.setLong(2, element.getManufacturer().getId());
            preparedStatement.setLong(3, element.getId());
            Car old = get(element.getId()).get();
            preparedStatement.executeUpdate();
            updateDriversByCar(element);
            return old;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update car " + element, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String selectQuery = "UPDATE cars SET deleted = true WHERE id = ?"
                + " and deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(
                             selectQuery)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete car by id " + id, e);
        }
    }

    //ok
    private Car getCar(ResultSet set) throws SQLException {
        String manufactureName = set.getNString("name");
        String manufactureCountry = set.getNString("country");
        Manufacturer manufacturer = new Manufacturer(manufactureName, manufactureCountry);
        manufacturer.setId(set.getObject("manufacture_id", Long.class));
        String model = set.getNString("model");
        Car car = new Car(model, manufacturer);
        car.setId(set.getObject("id", Long.class));
        car.setDrivers(getDriversByCarId(car.getId()));
        return car;
    }

    //ok
    private List<Driver> getDriversByCarId(Long carId) {
        String query = "SELECT driver_id AS id, name, license_number FROM cars_drivers "
                + "JOIN drivers d ON cars_drivers.driver_id = d.id "
                + "WHERE car_id = ? AND d.deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, carId);
            ResultSet resultSet = statement.executeQuery();
            List<Driver> drivers = new ArrayList<>();
            while (resultSet.next()) {
                drivers.add(getDriver(resultSet));
            }
            return drivers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't take data from DB", e);
        }
    }

    //OK
    private Driver getDriver(ResultSet resultSet) throws SQLException {
        Long droverId = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String licenseNumber = resultSet.getString("license_number");
        Driver driver = new Driver(name, licenseNumber);
        driver.setId(droverId);
        return driver;
    }

    private void updateDriversByCar(Car car) {
        insertAllDrivers(car.getId(), car.getDrivers());
        deleteAllDriversExceptList(car.getId(), car.getDrivers());
    }

    private void insertAllDrivers(Long carId, List<Driver> drivers) {
        if (drivers == null || drivers.size() == 0) {
            return;
        }
        StringBuilder transaction = new StringBuilder(
                "INSERT INTO cars_drivers (car_id, driver_id) VALUES ");
        for (int i = 0; i < drivers.size(); i++) {
            transaction.append("(?, ?)");
            if (i != drivers.size() - 1) {
                transaction.append(", ");
            }
        }
        transaction.append("ON DUPLICATE KEY UPDATE car_id = car_id");
        String insertQuery = transaction.toString();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(
                             insertQuery)) {
            for (int i = 0; i < drivers.size(); i++) {
                Driver driver = drivers.get(i);
                preparedStatement.setLong((i * 2) + 1, carId);
                preparedStatement.setLong((i * 2) + 2, driver.getId());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert drivers " + drivers, e);
        }
    }

    private void deleteAllDriversExceptList(Long carId, List<Driver> exceptions) {
        int size;
        if (exceptions == null) {
            size = 0;
        } else {
            size = exceptions.size();
        }
        StringBuilder transaction = new StringBuilder(
                "DELETE FROM cars_drivers WHERE car_id = ? AND NOT driver_id IN (0");
        for (int i = 0; i < size; i++) {
            transaction.append(", ?");
        }
        transaction.append(");");
        String insertQuery = transaction.toString();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(
                             insertQuery)) {
            preparedStatement.setLong(1, carId);
            for (int i = 0; i < size; i++) {
                Driver driver = exceptions.get(i);
                preparedStatement.setLong((i) + 2, driver.getId());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete drivers " + exceptions, e);
        }
    }

}

