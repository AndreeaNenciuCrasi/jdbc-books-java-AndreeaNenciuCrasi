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
        // TODO
    }

    @Override
    public void update(Author author) {
        // TODO
    }

    @Override
    public Author get(int id) {
        // TODO
        return null;
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

//                System.out.println("id" + id);
//                System.out.println("first name" + firstName);
//                System.out.println("last name" + lastName);
//                System.out.println("birth date" + birthDate);
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
        System.out.println("Goodbye!");

        return authors;
    }
}
