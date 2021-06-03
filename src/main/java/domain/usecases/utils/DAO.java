package domain.usecases.utils;

import java.util.List;
import java.util.Optional;

public interface DAO<T,K> {
    Optional<T> findOne(Integer key);
    List<T> fetchAll();
    K insert(T type);
    boolean update(K key,T type);
    boolean delete(T type);
    boolean deleteByKey(K key);
}
