package ru.otus.elena363404.service;
import ru.otus.elena363404.domain.Genre;

import java.util.List;

public interface GenreService {
  void createGenre();

  void updateGenre();

  void deleteGenre();

  void getGenreById();

  void getAllGenre();
}
