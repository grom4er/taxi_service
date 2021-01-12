package taxiservice.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    T create(T element);

    Optional<T> get(Long id);

    List<T> getAll();

    T update(T element);

    boolean delete(Long id);
}
