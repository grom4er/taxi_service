package taxiservice.dao.jbdc;


import taxiservice.dao.ManufacturerDao;
import taxiservice.exception.DataProcessingException;
import taxiservice.lib.Dao;
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
import java.util.zip.DataFormatException;

@Dao
public class ManufacturerJdbcDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer element) {
        String query = "INSERT INTO manufacturers " +
                "(manufacturer_name, manufacturer_country) VALUE (?, ?)";
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
            throw new DataProcessingException("text",e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers " +
                "WHERE manufacturer_id = ? AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Manufacturer manufacturer = getManufacturer(resultSet);
                return Optional.ofNullable(getManufacturer(resultSet));
            } else {
                throw new RuntimeException("");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("text",e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM manufacturers WHERE deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            List<Manufacturer> manufacturers = new ArrayList<>();
            while (resultSet.next()) {
                manufacturers.add(getManufacturer(resultSet));
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("text",e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer element) {
        String query = "UPDATE manufacturers SET manufacturer_name = ?, manufacturer_country = ? "
                + " WHERE manufacturer_id = ? AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, element.getName());
            statement.setString(2, element.getCountry());
            statement.setLong(3, element.getId());
            statement.executeUpdate();
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException("text",e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers " +
                "SET deleted = true WHERE manufacturer_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int deletedLines = statement.executeUpdate();
            return deletedLines > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("text",e);
        }
    }


    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Long manufacturerId = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        Manufacturer manufacturer = new Manufacturer(name, country);
        manufacturer.setId(manufacturerId);
        return manufacturer;
    }
}
