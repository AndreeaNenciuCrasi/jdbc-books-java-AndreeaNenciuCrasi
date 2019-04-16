package com.codecool.books.model;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDaoJDBC implements AuthorDao {
    private DataSource dataSource;

    public AuthorDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Author author) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO author (first_name, last_name, birth_date) VALUES (?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, author.getFirstName());
            st.setString(2, author.getLastName());
            st.setDate(3, author.getBirthDate());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            author.setId(rs.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Author author) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE author SET first_name = ?, last_name = ?, birth_date = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, author.getFirstName());
            st.setString(2, author.getLastName());
            st.setDate(3, author.getBirthDate());
            st.setInt(4, author.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Author get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT first_name, last_name, birth_date FROM author WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Author author = new Author(rs.getString(1), rs.getString(2), rs.getDate(3));
            author.setId(id);
            return author;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Author> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, first_name, last_name, birth_date FROM author";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Author> result = new ArrayList<>();
            while (rs.next()) {
                Author author = new Author(rs.getString(2), rs.getString(3), rs.getDate(4));
                author.setId(rs.getInt(1));
                result.add(author);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
