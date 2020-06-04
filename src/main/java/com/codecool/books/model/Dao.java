package com.codecool.books.model;

import java.util.List;

public interface Dao<T> {
    /**
     * Add a new object to database, and set the new ID
     *
     * @param object a new object, with ID not set yet (null)
     */
    void add(T object);

    /**
     * Update existing object's data in the database
     *
     * @param object an object from the database, with ID already set
     */
    void update(T object);

    /**
     * Get object by ID
     *
     * @param id ID to search by
     * @return Object with a given ID, or null if not found
     */
    T get(int id);

    /**
     * Get all objects
     *
     * @return List of all objects of this type in the database
     */
    List<T> getAll();
}
