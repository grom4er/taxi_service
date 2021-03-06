package taxiservice.service;

import java.util.List;

public interface GenericService<T> {
    T create(T type);

    T get(Long id);

    List<T> getAll();

    T update(T type);

    boolean delete(Long id);
}
