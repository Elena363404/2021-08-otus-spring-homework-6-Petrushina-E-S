package ru.otus.elena363404.dao;

import ru.otus.elena363404.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

  void saveBook(Book book);

  Optional<Book> getBookById(long id);

  void deleteBookById(long id);

  List<Book> getAllBookByAuthorId(long id);

  List<Book> getAllBook();

}
