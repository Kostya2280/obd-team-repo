import java.util.List;

public interface GenericDAO<T, K> {
    void create(T entity);
    T getById(K id);
    List<T> getAll();
    void update(T entity);
    void delete(K id);
}
