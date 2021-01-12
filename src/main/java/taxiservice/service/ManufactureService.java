package taxiservice.service;

import java.util.List;
import taxiservice.models.Manufacture;

public interface ManufactureService extends Service<Manufacture> {
    @Override
    Manufacture create(Manufacture manufacture);

    @Override
    Manufacture get(Long id);

    @Override
    List<Manufacture> getAll();

    @Override
    Manufacture update(Manufacture manufacture);

    @Override
    boolean delete(Long id);
}
