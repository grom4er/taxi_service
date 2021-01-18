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
import taxiservice.models.Driver;
import taxiservice.util.ConnectionUtil;

public class DriverJdbcDaoIml implements DriverDao {
    @Override
    public Driver create(Driver element) {
        String query = "INSERT INTO drivers (name, license_number) VALUE (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, element.getName());
            statement.setString(2, element.getLicenseNumber());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                element.setId(resultSet.getObject(1, Long.class));
            }
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException(String.format("Data %s can't add to table", element), e);
        }
    }

    @Override
    public Optional<Driver> get(Long id) {
        String query = "SELECT * FROM drivers "
                + "WHERE id = ? AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.ofNullable(getDriver(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't take data with id " + id, e);
        }
    }

    @Override
    public List<Driver> getAll() {
        String query = "SELECT * FROM drivers WHERE deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
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

    @Override
    public Driver update(Driver element) {
        String query = "UPDATE drivers SET name = ?, license_number = ? "
                + " WHERE id = ? AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, element.getName());
            statement.setString(2, element.getLicenseNumber());
            statement.setLong(3, element.getId());
            statement.executeUpdate();
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    String.format("Can't update data %s in DB", element), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE drivers "
                + "SET deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    String.format("Can't delete data with id %d from DB", id), e);
        }
    }

    private Driver getDriver(ResultSet resultSet) throws SQLException {
        Long droverId = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String licenseNumber = resultSet.getString("license_number");
        Driver driver = new Driver(name, licenseNumber);
        driver.setId(droverId);
        return driver;
    }
}
