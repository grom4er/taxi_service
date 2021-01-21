package taxiservice.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, I> {
    T create(T element);

    Optional<T> get(I id);

    List<T> getAll();

    T update(T element);

    boolean delete(I id);
}
