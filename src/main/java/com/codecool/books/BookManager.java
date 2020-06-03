package com.codecool.books.view;

import com.codecool.books.Manager;
import com.codecool.books.model.Author;
import com.codecool.books.model.AuthorDao;
import com.codecool.books.model.Book;
import com.codecool.books.model.BookDao;

import java.sql.Date;

public class BookManager extends Manager {
    BookDao bookDao;
    Author authorDao;

    public BookManager(UserInterface ui, BookDao bookDao, Author authorDao) {
        super(ui);
        this.bookDao = bookDao;
        this.authorDao = authorDao;
    }

    @Override
    protected void add() {
        String title = ui.readString("Book title", "X");
        int authorId = ui.readInt("Author id", 1);
        bookDao.add(new Book(authorDao.get(authorId), title));
    }

    @Override
    protected String getName() {
        return "Author Manager";
    }

    @Override
    protected void list() {
        for (Author author: authorDao.getAll()) {
            ui.println(author);
        }
    }

    @Override
    protected void edit() {
        int id = ui.readInt("Author ID", 0);
        Author author = authorDao.get(id);
        if (author == null) {
            ui.println("Author not found!");
            return;
        }
        ui.println(author);

        String firstName = ui.readString("First name", author.getFirstName());
        String lastName = ui.readString("Last name", author.getLastName());
        Date birthDate = ui.readDate("Birth date", author.getBirthDate());
        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.setBirthDate(birthDate);
        authorDao.update(author);
    }
}
