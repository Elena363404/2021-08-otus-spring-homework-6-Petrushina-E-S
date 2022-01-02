package ru.otus.elena363404.service;

import ru.otus.elena363404.domain.Author;

import java.util.List;


public interface AuthorService {

  void createAuthor();

  void updateAuthor();

  void deleteAuthor();

  void getAuthorById();

  void getAllAuthor();
}
