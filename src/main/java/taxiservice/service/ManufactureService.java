package taxiservice.service;

import java.util.List;
import taxiservice.models.Manufacturer;

public interface ManufactureService extends Service<Manufacturer> {
    @Override
    Manufacturer create(Manufacturer manufacturer);

    @Override
    Manufacturer get(Long id);

    @Override
    List<Manufacturer> getAll();

    @Override
    Manufacturer update(Manufacturer manufacturer);

    @Override
    boolean delete(Long id);
}
