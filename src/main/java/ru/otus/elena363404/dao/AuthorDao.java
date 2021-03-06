package ru.otus.elena363404.dao;

import ru.otus.elena363404.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

  void saveAuthor(Author author);

  Optional<Author> getAuthorById(long id);

  void deleteAuthorById(long id);

  List<Author> getAllAuthor();
}
