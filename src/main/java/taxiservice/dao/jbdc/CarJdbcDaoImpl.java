package taxiservice.dao.jbdc;

import taxiservice.dao.CarDao;
import taxiservice.lib.Dao;
import taxiservice.models.Car;
import taxiservice.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Dao
public class CarJdbcDaoImpl implements CarDao {

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
                car.setId(resultSet.getObject(1, Long.class));
            }
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    String.format("Data %s can't add to table", element), e);
        }
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
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
/*


@Dao
public class ManufacturerJdbcDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer element) {
        String query = "INSERT INTO manufacturers "
                + "(name, country) VALUE (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                         Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, element.getName());
            statement.setString(2, element.getCountry());
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

 */