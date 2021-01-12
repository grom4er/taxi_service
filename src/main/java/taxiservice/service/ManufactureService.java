package taxiservice.service;

import java.util.List;
import taxiservice.models.Manufacturer;

public interface ManufactureService extends Service<Manufacturer> {
    Manufacturer create(Manufacturer manufacturer);

    Manufacturer get(Long id);

    List<Manufacturer> getAll();

    Manufacturer update(Manufacturer manufacturer);

    boolean delete(Long id);
}
