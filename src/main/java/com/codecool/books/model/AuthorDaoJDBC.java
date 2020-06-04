package com.codecool.books.model;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDaoJDBC implements Dao<Author> {
    private DataSource dataSource;

    public AuthorDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Author author) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            String query = String.format("INSERT INTO author (first_name, last_name, birth_date) VALUES ('%s', '%s', '%s');",
                    author.getFirstName(), author.getLastName(), author.getBirthDate());
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
    public void update(Author author) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            String query = String.format("UPDATE author SET " +
                            "(first_name, last_name, birth_date) = ('%s','%s', '%s') WHERE author.id = '%d'",
                    author.getFirstName(), author.getLastName(), author.getBirthDate(), author.getId());
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
    public Author get(int id) {
        Connection connection = null;
        Statement statement = null;
        Author author = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            String query = String.format("SELECT * FROM author WHERE author.id = '%d';", id);
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Date birthDate = resultSet.getDate("birth_date");
                author = new Author(firstName, lastName, birthDate);
                author.setId(id);
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
        return author;
    }

    @Override
    public List<Author> getAll() {
        Connection connection = null;
        Statement statement = null;
        List<Author> authors= new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM author;");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Date birthDate = resultSet.getDate("birth_date");
                Author author = new Author(firstName, lastName,birthDate);
                author.setId(id);
                authors.add(author);
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
        return authors;
    }
}
