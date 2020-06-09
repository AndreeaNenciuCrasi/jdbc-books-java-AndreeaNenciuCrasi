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
        try {
            Connection connection = dataSource.getConnection();
            String sql = "INSERT INTO book (author_id, title) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, book.getAuthor().getId());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Book book) {
        try{
        Connection connection = dataSource.getConnection();
        String sql = "UPDATE book SET author_id = ?, title = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,book.getAuthor().getId());
        preparedStatement.setString(2,book.getTitle());
        preparedStatement.setInt(3,book.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Book get(int id) {
        Book book = null;
        try{
        Connection connection = dataSource.getConnection();
        String sql ="Select * from book where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            int authorId = resultSet.getInt("author_id");
            String title = resultSet.getString("title");
            book = new Book(authorDao.get(authorId),title);
            book.setId(id);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
            String sql = "SELECT * FROM book";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
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
