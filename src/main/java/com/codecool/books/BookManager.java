package com.codecool.books;

import com.codecool.books.model.Author;
import com.codecool.books.model.Book;
import com.codecool.books.model.Dao;
import com.codecool.books.view.UserInterface;

public class BookManager extends Manager {
    Dao<Book> bookDao;
    Dao<Author> authorDao;

    public BookManager(UserInterface ui, Dao<Book> bookDao, Dao<Author> authorDao) {
        super(ui);
        this.bookDao = bookDao;
        this.authorDao = authorDao;
    }

    @Override
    protected void add() {
        String title = ui.readString("Book title", "X");
        String authorId = ui.readString("Author id", "1");
        bookDao.add(new Book(authorDao.get(Integer.parseInt(authorId)), title));
    }

    @Override
    protected String getName() {
        return "Book Manager";
    }

    @Override
    protected void list() {
        for (Book book: bookDao.getAll()) {
            ui.println(book);
        }
    }

    @Override
    protected void edit() {
        int id = ui.readInt("Book ID", 0);
        Book book = bookDao.get(id);
        if (book == null) {
            ui.println("Book not found!");
            return;
        }
        ui.println(book);

        String title = ui.readString("Book title", book.getTitle());
        int authorId = ui.readInt("Author Id", book.getId());

        book.setTitle(title);
        book.setId(authorId);
        bookDao.update(book);
    }
}
