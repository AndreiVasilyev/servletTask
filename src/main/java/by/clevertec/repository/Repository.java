package by.clevertec.repository;

import java.util.List;

public interface Repository<T> {
    List<T> findAll();

    T findById(String id);

    T add(T entity);

    T update(T entity);

    T delete(String id);
}
