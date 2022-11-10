package be.ucll.java.ent.repository;

import java.util.List;
import java.util.Optional;

// Generic Data Access Object with CRUD-S methods
public interface Dao<T> {

    // Create 1 object
    void create(T t);

    // Read 1 object
    Optional<T> get(long id);

    T read(long id);

    // Update 1 object
    void update(T t);

    // Delete 1 object
    void delete(long id);

    // Search/Return all objects
    List<T> getAll();

    // Count the nr of objects
    long countAll();
}
