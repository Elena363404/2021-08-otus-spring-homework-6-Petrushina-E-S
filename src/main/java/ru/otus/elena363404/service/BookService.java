package ru.otus.elena363404.service;

import ru.otus.elena363404.domain.Book;

public interface BookService {

  void createBook();

  void updateBook();

  void deleteBook();

  void getBookById();

  void getAllBook();
}
