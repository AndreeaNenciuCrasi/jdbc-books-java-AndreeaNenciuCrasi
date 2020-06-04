package com.codecool.books.model;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoJDBC implements Dao<Book>{
    private DataSource dataSource;
    Dao<Author> authorDao;

    public BookDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Book book) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            String query = String.format("INSERT INTO book (id, author_id, title) VALUES ('%d', '%d', '%s');",
                    book.getId(), book.getAuthor().getId(), book.getTitle());
            statement.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException e) {
            }// do nothing
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Book book) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            String query = String.format("UPDATE author SET " +
                            "(id, author_is, title) = ('%d','%d', '%s') WHERE book.id = '%d'",
                    book.getId(), book.getAuthor().getId(), book.getTitle());
            statement.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException e) {
            }// do nothing
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Book get(int id) {
        Connection connection = null;
        Statement statement = null;
        Book book = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            String query = String.format("SELECT * FROM book WHERE book.id = '%d';", id);
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int authorId = resultSet.getInt("author_id");
                String title = resultSet.getString("title");

                book = new Book(authorDao.get(authorId),title);
                book.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException e) {
            }// do nothing
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return book;
    }

    @Override
    public List<Book> getAll() {
        Connection connection = null;
        Statement statement = null;
        List<Book> books= new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM book;");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int authorId = resultSet.getInt("author_id");
                String title = resultSet.getString("title");

                Book book = new Book(authorDao.get(authorId),title);
                book.setId(id);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException e) {
            }// do nothing
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return books;

    }
}
