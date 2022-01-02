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
import ru.otus.elena363404.domain.Book;
import ru.otus.elena363404.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@Import({BookDaoJpa.class, AuthorDaoJpa.class, GenreDaoJpa.class})
class BookDaoJpaTest {

  private static final long EXISTING_BOOK_ID = 3L;
  private static final int EXPECTED_NUMBER_OF_BOOKS = 5;
  private static final int EXPECTED_QUERIES_COUNT = 1;
  private static final int BOOK_ID_FOR_DELETE = 2;

  @Autowired
  BookDaoJpa bookDao;

  @Autowired
  private TestEntityManager em;

  @DisplayName("Add book in the DB")
  @Test
  void shouldInsertBook() {
    Book expectedBook = new Book(6,"BookForTest", new Author(2,"Alexander Pushkin"), new Genre(3,"Novel"), null);
    bookDao.createBook(expectedBook);
    Book actualBook = bookDao.getBookById(expectedBook.getId()).stream().findFirst().orElse(null);
    assertThat(actualBook).isEqualTo(expectedBook);
  }

  @DisplayName("Return book by ID")
  @Test
  void shouldReturnExpectedBookById() {
    val optionalActualBook = bookDao.getBookById(EXISTING_BOOK_ID);
    val expectedBook = em.find(Book.class, EXISTING_BOOK_ID);
    assertThat(optionalActualBook).isPresent().get().
      usingRecursiveComparison().isEqualTo(expectedBook);
  }

  @DisplayName("Delete book by ID")
  @Test
  void shouldCorrectDeleteBookById() {
    Book bookBeforeDelete = bookDao.getBookById(BOOK_ID_FOR_DELETE).stream().findFirst().orElse(null);
    assertNotNull(bookBeforeDelete);
    bookDao.deleteBookById(BOOK_ID_FOR_DELETE);
    em.remove(bookBeforeDelete);
    Book bookAfterDelete = bookDao.getBookById(BOOK_ID_FOR_DELETE).stream().findFirst().orElse(null);
    assertNull(bookAfterDelete);
  }

  @DisplayName("Return list of the books")
  @Test
  void shouldReturnExpectedBooksList() {
    SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
      .unwrap(SessionFactory.class);
    sessionFactory.getStatistics().setStatisticsEnabled(true);


    System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
    val books = bookDao.getAllBook();
    assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
      .allMatch(s -> !s.getName().equals(""))
      .allMatch(s -> s.getAuthor() != null)
      .allMatch(s -> s.getGenre() != null);
    System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
    assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
  }
}