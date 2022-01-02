package ru.otus.elena363404.dao;

import ru.otus.elena363404.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

  void createGenre(Genre genre);

  Optional<Genre> getGenreById(long id);

  void updateGenre(Genre genre);

  void deleteGenreById(long id);

  List<Genre> getAllGenre();
}
