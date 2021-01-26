package taxiservice.dao.jbdc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import taxiservice.dao.DriverDao;
import taxiservice.exception.DataProcessingException;
import taxiservice.lib.Dao;
import taxiservice.models.Driver;
import taxiservice.util.ConnectionUtil;

@Dao
public class DriverJdbcDaoIml implements DriverDao {
    @Override
    public Driver create(Driver driver) {
        String insertQuery = "INSERT INTO drivers (name, license_number, login, password)"
                + "VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(
                             insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, driver.getName());
            preparedStatement.setString(2, driver.getLicenseNumber());
            preparedStatement.setString(3, driver.getLogin());
            preparedStatement.setString(4, driver.getPassword());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                driver.setId(resultSet.getObject(1, Long.class));
            }
            return driver;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create driver " + driver, e);
        }
    }

    @Override
    public Optional<Driver> get(Long id) {
        String selectQuery = "SELECT * FROM drivers where id = ? and deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.ofNullable(parseFromResultSet(resultSet));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get driver by id: " + id, e);
        }
    }

    @Override
    public List<Driver> getAll() {
        String selectQuery = "SELECT * FROM drivers where deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Driver> drivers = new ArrayList<>();
            while (resultSet.next()) {
                drivers.add(parseFromResultSet(resultSet));
            }
            return drivers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all drivers", e);
        }
    }

    @Override
    public Driver update(Driver driver) {
        String selectQuery = "UPDATE drivers SET name = ?, license_number = ?,"
                + " login = ?, password = ? WHERE id = ?"
                + " and deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, driver.getName());
            preparedStatement.setString(2, driver.getLicenseNumber());
            preparedStatement.setString(3, driver.getLogin());
            preparedStatement.setString(4, driver.getPassword());
            preparedStatement.setLong(5, driver.getId());
            preparedStatement.executeUpdate();
            return driver;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update driver " + driver, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String selectQuery = "UPDATE drivers SET deleted = true WHERE id = ?"
                + " and deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete driver by id " + id, e);
        }
    }

    private Driver parseFromResultSet(ResultSet resultSet) throws SQLException {
        Long driverId = resultSet.getObject("id", Long.class);
        if (driverId == null) {
            return null;
        }
        String name = resultSet.getNString("name");
        String licenseNumber = resultSet.getNString("license_number");
        String login = resultSet.getNString("login");
        String password = resultSet.getNString("password");
        Driver driver = new Driver(name, licenseNumber);
        driver.setLogin(login);
        driver.setPassword(password);
        driver.setId(driverId);
        return driver;
    }

    @Override
    public Optional<Driver> findByLogin(String login) {
        String selectQuery = "SELECT * FROM drivers where login LIKE ? and deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.ofNullable(parseFromResultSet(resultSet));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get driver by login: " + login, e);
        }
    }
}
