package taxiservice.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import taxiservice.db.Storage;

public class Car {
    private Long id;
    private String model;
    private Manufacture manufacture;
    private List<Driver> drivers;

    public Car(String model, Manufacture manufacture) {
        id = Integer.toUnsignedLong(Storage.getManufactureStorage().size() + 1);
        this.model = model;
        this.manufacture = manufacture;
        drivers = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Manufacture getManufacture() {
        return manufacture;
    }

    public void setManufacture(Manufacture manufacture) {
        this.manufacture = manufacture;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return Objects.equals(id, car.id)
                && Objects.equals(model, car.model)
                && Objects.equals(manufacture, car.manufacture)
                && Objects.equals(drivers, car.drivers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, manufacture, drivers);
    }

    @Override
    public String toString() {
        return "Car{"
                + "id=" + id
                + ", model='" + model + '\''
                + ", manufacture=" + manufacture
                + ", drivers=" + drivers
                + '}';
    }
}
