package com.codecool.books.model;

import javax.sql.DataSource;
import java.util.List;

public class BookDaoJDBC implements Dao<Book>{
    private DataSource dataSource;

    public BookDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Book book) {
        // TODO
    }

    @Override
    public void update(Book book) {
        // TODO
    }

    @Override
    public Book get(int id) {
        // TODO
        return null;
    }

    @Override
    public List<Book> getAll() {
        // TODO
        return null;
    }
}
