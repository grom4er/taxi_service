package taxiservice.dao.jbdc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import taxiservice.dao.CarDao;
import taxiservice.exception.DataProcessingException;
import taxiservice.lib.Dao;
import taxiservice.models.Car;
import taxiservice.models.Driver;
import taxiservice.models.Manufacturer;
import taxiservice.util.ConnectionUtil;

@Dao
public class CarJdbcDaoImpl implements CarDao {
    @Override
    public Car create(Car car) {
        String insertQuery = "INSERT INTO cars (model, manufacturer_id)"
                + "VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(
                             insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, car.getModel());
            preparedStatement.setLong(2, car.getManufacturer().getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                car.setId(resultSet.getObject(1, Long.class));
            }
            insertAllDrivers(car.getId(), car.getDrivers());
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create car " + car, e);
        }
    }

    @Override
    public Optional<Car> get(Long id) {
        String selectQuery = "SELECT c.id as id, "
                + "model, "
                + "manufacturer_id, "
                + "m.name as manufacturer_name, "
                + "m.country as manufacturer_country "
                + "FROM cars c"
                + " LEFT JOIN manufacturers m on c.manufacturer_id = m.id"
                + " where c.id = ? AND c.deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                        connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(parseCarFromResultSetAndSetDriversToCar(resultSet));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get car by id: " + id, e);
        }
    }

    @Override
    public List<Car> getAll() {
        String selectQuery = "SELECT c.id as id, "
                + "model, "
                + "manufacturer_id, "
                + "m.name as manufacturer_name, "
                + "m.country as manufacturer_country "
                + "FROM cars c"
                + " LEFT JOIN manufacturers m on c.manufacturer_id = m.id"
                + " where c.deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                cars.add(parseCarFromResultSetAndSetDriversToCar(resultSet));
            }
            return cars;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all cars", e);
        }
    }

    @Override
    public Car update(Car car) {
        String query = "UPDATE cars SET model = ?, manufacturer_id = ? WHERE id = ?"
                + " and deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, car.getModel());
            preparedStatement.setLong(2, car.getManufacturer().getId());
            preparedStatement.setLong(3, car.getId());
            preparedStatement.executeUpdate();
            Car old = get(car.getId()).get();
            updateDriversByCar(car);
            return old;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update car " + car, e);
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

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        String selectQuery = "SELECT c.id AS id, "
                + "model, "
                + "manufacturer_id, "
                + "m.name as manufacturer_name, "
                + "m.country as manufacturer_country "
                + "FROM cars c"
                + " LEFT JOIN manufacturers m ON c.manufacturer_id = m.id"
                + " JOIN cars_drivers cd ON c.id = cd.car_id"
                + " JOIN drivers d ON cd.driver_id = d.id"
                + " WHERE c.deleted = false AND d.deleted = false AND driver_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, driverId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                cars.add(parseCarFromResultSetAndSetDriversToCar(resultSet));
            }
            return cars;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all cars", e);
        }
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
                         connection.prepareStatement(insertQuery)) {
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

    private void deleteAllDriversExceptList(Long carId, List<Driver> driversNotDeleteList) {
        int size;
        if (driversNotDeleteList == null) {
            size = 0;
        } else {
            size = driversNotDeleteList.size();
        }
        StringBuilder transaction = new StringBuilder(
                "DELETE FROM cars_drivers WHERE car_id = ? AND NOT driver_id IN (0");
        for (int i = 0; i < size; i++) {
            transaction.append(", ?");
        }
        transaction.append(");");
        String insertQuery = transaction.toString();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setLong(1, carId);
            for (int i = 0; i < size; i++) {
                Driver driver = driversNotDeleteList.get(i);
                preparedStatement.setLong((i) + 2, driver.getId());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete drivers " + driversNotDeleteList, e);
        }
    }

    private List<Driver> getAllDriversByCarId(Long carId) {
        String selectQuery = "SELECT driver_id as id, name, license_number FROM cars_drivers "
                + "join drivers d on cars_drivers.driver_id = d.id "
                + "where car_id = ? AND d.deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement preparedStatement =
                         connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, carId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Driver> drivers = new ArrayList<>();
            while (resultSet.next()) {
                drivers.add(parseDriverFromResultSet(resultSet));
            }
            return drivers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all drivers by car id" + carId, e);
        }
    }

    private Driver parseDriverFromResultSet(ResultSet resultSet) throws SQLException {
        long driverId = resultSet.getLong("id");
        String name = resultSet.getNString("name");
        String licenseNumber = resultSet.getNString("license_number");
        Driver driver = new Driver(name, licenseNumber);
        driver.setId(driverId);
        return driver;
    }

    private Car parseCarFromResultSetAndSetDriversToCar(ResultSet resultSet) throws SQLException {
        long manufacturerId = resultSet.getLong("manufacturer_id");
        String manufacturerName = resultSet.getNString("manufacturer_name");
        String manufacturerCountry = resultSet.getNString("manufacturer_country");
        Manufacturer manufacturer = new Manufacturer(manufacturerName, manufacturerCountry);
        manufacturer.setId(manufacturerId);
        long carId = resultSet.getLong("id");
        String model = resultSet.getNString("model");
        Car car = new Car(model, manufacturer);
        car.setId(carId);
        car.setDrivers(getAllDriversByCarId(carId));
        return car;
    }
}
