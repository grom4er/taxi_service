package taxiservice.service;

import java.util.List;
import taxiservice.dao.ManufactureDao;
import taxiservice.lib.Inject;
import taxiservice.lib.ServiceImpl;
import taxiservice.models.Manufacture;

@ServiceImpl
public class ManufactureServiceImpl implements ManufactureService {
    @Inject
    private ManufactureDao manufacture;

    @Override
    public Manufacture create(Manufacture manufacture) {
        return this.manufacture.create(manufacture);
    }

    @Override
    public Manufacture get(Long id) {
        return manufacture.get(id).orElseThrow(
                () -> new RuntimeException(String.format("%d not found", id)));
    }

    @Override
    public List<Manufacture> getAll() {
        return manufacture.getAll();
    }

    @Override
    public Manufacture update(Manufacture manufacture) {
        return this.manufacture.update(manufacture);
    }

    @Override
    public boolean delete(Long id) {
        return manufacture.delete(id);
    }
}
