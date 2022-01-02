package ru.otus.elena363404.dao;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.elena363404.domain.Genre;

import javax.persistence.Query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


@DataJpaTest
@Import(GenreDaoJpa.class)
class GenreDaoJpaTest {

  private static final long EXISTING_GENRE_ID = 3L;
  private static final int GENRE_ID_FOR_DELETE = 5;
  private static final int EXPECTED_QUERIES_COUNT = 1;
  private static final int EXPECTES_NUMBER_OF_GENRES = 5;

  @Autowired
  private GenreDaoJpa genreDao;

  @Autowired
  private GenreDaoJpa bookDao;

  @Autowired
  private TestEntityManager em;

  @DisplayName("Add genre in the DB")
  @Test
  void shouldInsertGenre() {
    Genre expectedGenre = new Genre(5, "Thriller");
    genreDao.createGenre(expectedGenre);
    Genre actualGenre = genreDao.getGenreById(expectedGenre.getId()).stream().findFirst().orElse(null);
    assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
  }

  @DisplayName("Return genre by ID")
  @Test
  void shouldReturnExpectedGenreById() {
    val optionalActualGenre = genreDao.getGenreById(EXISTING_GENRE_ID);
    val expectedGenre = em.find(Genre.class, EXISTING_GENRE_ID);
    assertThat(optionalActualGenre).isPresent().get().
      usingRecursiveComparison().isEqualTo(expectedGenre);
  }

  @DisplayName("Delete genre by ID")
  @Test
  void shouldCorrectDeleteGenreById() {
    Genre genreBeforeDelete = genreDao.getGenreById(GENRE_ID_FOR_DELETE).stream().findFirst().orElse(null);
    genreDao.deleteGenreById(GENRE_ID_FOR_DELETE);
    em.remove(genreBeforeDelete);
    Genre genreAfterDelete = genreDao.getGenreById(GENRE_ID_FOR_DELETE).stream().findFirst().orElse(null);
    assertNull(genreAfterDelete);
  }

  @DisplayName("Return list of the genres")
  @Test
  void shouldReturnExpectedGenresList() {
    SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
      .unwrap(SessionFactory.class);
    sessionFactory.getStatistics().setStatisticsEnabled(true);

    System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
    val genres = genreDao.getAllGenre();
    assertThat(genres).isNotNull().hasSize(EXPECTES_NUMBER_OF_GENRES)
      .allMatch(s -> !s.getName().equals(""));
    System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
    assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
  }

}