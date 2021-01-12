package taxiservice.service;

import java.util.List;
import taxiservice.dao.ManufacturerDao;
import taxiservice.lib.Inject;
import taxiservice.lib.ServiceImpl;
import taxiservice.models.Manufacturer;

@ServiceImpl
public class ManufactureServiceImpl implements ManufactureService {
    @Inject
    private ManufacturerDao manufacture;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        return this.manufacture.create(manufacturer);
    }

    @Override
    public Manufacturer get(Long id) {
        return manufacture.get(id).orElseThrow(
                () -> new RuntimeException(String.format("%d not found", id)));
    }

    @Override
    public List<Manufacturer> getAll() {
        return manufacture.getAll();
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return this.manufacture.update(manufacturer);
    }

    @Override
    public boolean delete(Long id) {
        return manufacture.delete(id);
    }
}
