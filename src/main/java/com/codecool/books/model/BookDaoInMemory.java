package com.codecool.books.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookDaoInMemory implements Dao<Book>{
    Map<Integer, Book> books = new HashMap<>();
    int idCounter = 0;

    @Override
    public void add(Book book) {
        book.setId(idCounter);
        idCounter++;
        books.put(book.getId(), book);
    }

    @Override
    public void update(Book book) {
        books.put(book.getId(), book);
    }

    @Override
    public Book get(int id) {
        return books.get(id);
    }

    @Override
    public List<Book> getAll() {
        return new ArrayList<>(books.values());
    }
}
