package ru.otus.elena363404.dao;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.elena363404.domain.Author;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(AuthorDaoJpa.class)
class AuthorDaoJpaTest {

  private static final long EXISTING_AUTHOR_ID = 3L;
  private static final int EXPECTES_NUMBER_OF_AUTHORS = 5;
  private static final int EXPECTED_QUERIES_COUNT = 1;
  private static final int AUTHOR_ID_FOR_DELETE = 5;

  @Autowired
  private AuthorDaoJpa authorDao;

  @Autowired
  private TestEntityManager em;

  @DisplayName("Add author in the DB")
  @Test
  void shouldInsertAuthor() {
    Author expectedAuthor = new Author(5,"Lermontov");
    authorDao.saveAuthor(expectedAuthor);
    Optional<Author> actualAuthor = authorDao.getAuthorById(expectedAuthor.getId());
    assertThat(actualAuthor.stream().findFirst().orElse(null)).usingRecursiveComparison().isEqualTo(expectedAuthor);
  }

  @DisplayName("Return author by ID")
  @Test
  void shouldReturnExpectedAuthorById() {
    val optionalActualAuthor = authorDao.getAuthorById(EXISTING_AUTHOR_ID);
    val expectedAuthor = em.find(Author.class, EXISTING_AUTHOR_ID);
    assertThat(optionalActualAuthor).isPresent().get().
      usingRecursiveComparison().isEqualTo(expectedAuthor);
  }

  @DisplayName("Delete author by ID")
  @Test
  void shouldCorrectDeleteAuthorById() {
    Author authorBeforeDelete = authorDao.getAuthorById(AUTHOR_ID_FOR_DELETE).stream().findFirst().orElse(null);
    assertNotNull(authorBeforeDelete);
    authorDao.deleteAuthorById(AUTHOR_ID_FOR_DELETE);
    em.remove(authorBeforeDelete);
    Author authorAfterDelete = authorDao.getAuthorById(AUTHOR_ID_FOR_DELETE).stream().findFirst().orElse(null);
    assertNull(authorAfterDelete);
  }

  @DisplayName("Return list of the authors")
  @Test
  void shouldReturnExpectedAuthorsList() {
    SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
      .unwrap(SessionFactory.class);
    sessionFactory.getStatistics().setStatisticsEnabled(true);

    System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
    val authors = authorDao.getAllAuthor();
    assertThat(authors).isNotNull().hasSize(EXPECTES_NUMBER_OF_AUTHORS)
      .allMatch(s -> !s.getName().equals(""));
    System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
    assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
  }



}