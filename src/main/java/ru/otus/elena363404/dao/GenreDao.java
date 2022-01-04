package ru.otus.elena363404.dao;

import ru.otus.elena363404.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

  void saveGenre(Genre genre);

  Optional<Genre> getGenreById(long id);

  void deleteGenreById(long id);

  List<Genre> getAllGenre();
}
