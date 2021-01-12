package taxiservice.service;

import java.util.List;
import taxiservice.dao.ManufacturerGenericDao;
import taxiservice.lib.Inject;
import taxiservice.lib.GenericService;
import taxiservice.models.Manufacturer;

@GenericService
public class ManufactureServiceImpl implements ManufactureService {
    @Inject
    private ManufacturerGenericDao manufactureDao;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        return this.manufactureDao.create(manufacturer);
    }

    @Override
    public Manufacturer get(Long id) {
        return manufactureDao.get(id).orElseThrow(
                () -> new RuntimeException(
                        String.format("Manufacturer by %d not found", id)));
    }

    @Override
    public List<Manufacturer> getAll() {
        return manufactureDao.getAll();
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return manufactureDao.update(manufacturer);
    }

    @Override
    public boolean delete(Long id) {
        return manufactureDao.delete(id);
    }
}
