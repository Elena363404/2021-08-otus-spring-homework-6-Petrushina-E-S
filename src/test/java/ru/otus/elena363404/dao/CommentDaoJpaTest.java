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
import ru.otus.elena363404.domain.Comment;
import ru.otus.elena363404.domain.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({CommentDaoJpa.class, BookDaoJpa.class})
class CommentDaoJpaTest {

  private static final long EXISTING_COMMENT_ID = 2L;
  private static final int EXISTING_BOOK_ID = 1;
  private static final int COMMENT_ID_FOR_DELETE = 3;
  private static final int COMMENT_ID_FOR_UPDATE = 2;
  private static final int EXPECTED_NUMBER_OF_COMMENTS = 3;
  private static final int EXPECTED_QUERIES_COUNT = 1;

  @Autowired
  private TestEntityManager em;

  @Autowired
  private CommentDaoJpa commentDao;

  @Autowired
  private BookDaoJpa bookDao;

  @DisplayName("Add comment in the DB")
  @Test
  void shouldInsertComment() {
    Comment expectedComment = new Comment(4,"Norm", bookDao.getBookById(2).stream().findFirst().orElse(null));
    commentDao.saveComment(expectedComment);
    Comment actualComment = commentDao.getCommentById(expectedComment.getId()).stream().findFirst().orElse(null);
    assertThat(actualComment).isEqualTo(expectedComment);
  }

  @DisplayName("Return comment by ID")
  @Test
  void shouldReturnExpectedCommentById() {
    val optionalActualComment = commentDao.getCommentById(EXISTING_COMMENT_ID);
    val expextedComment = em.find(Comment.class, EXISTING_COMMENT_ID);
    assertThat(optionalActualComment).isPresent().get().
      usingRecursiveComparison().isEqualTo(expextedComment);
  }

  @DisplayName("Return comment by book ID")
  @Test
  void shouldReturnExpectedCommentByBookId() {
    val optionalActualComment = commentDao.getAllCommentByBookId(EXISTING_BOOK_ID);
    List<Comment> commentList = getCommentListByBookId(EXISTING_BOOK_ID);

    assertThat(commentList).isEqualTo(optionalActualComment);
  }

  @DisplayName("Update comment by ID")
  @Test
  void shouldUpdateExpectedCommentById() {
    Comment newComment = new Comment(COMMENT_ID_FOR_UPDATE, "Comment after update!", bookDao.getBookById(2).stream().findFirst().orElse(null));
    commentDao.saveComment(newComment);
    Comment updatedComment = commentDao.getCommentById(COMMENT_ID_FOR_UPDATE).stream().findFirst().orElse(null);

    assertThat(newComment).isEqualTo(updatedComment);
  }

  @DisplayName("Delete comment by ID")
  @Test
  void shouldCorrectDeleteCommentById() {
    Comment commentBeforeDelete = commentDao.getCommentById(COMMENT_ID_FOR_DELETE).stream().findFirst().orElse(null);
    assertNotNull(commentBeforeDelete);
    commentDao.deleteCommentById(COMMENT_ID_FOR_DELETE);
    em.remove(commentBeforeDelete);
    Comment commentAfterDelete = commentDao.getCommentById(COMMENT_ID_FOR_DELETE).stream().findFirst().orElse(null);
    assertNull(commentAfterDelete);
  }

  @DisplayName("Return list of the comments")
  @Test
  void shouldReturnExpectedCommentsList() {
    SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
      .unwrap(SessionFactory.class);
    sessionFactory.getStatistics().setStatisticsEnabled(true);

    System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
    val comments = commentDao.getAllComment();
    assertThat(comments).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENTS)
      .allMatch(s -> !s.getComment().equals(""));
    System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
    assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
  }

  private List<Comment> getCommentListByBookId(long bookId) {
    Book book = bookDao.getBookById(bookId).stream().findFirst().orElse(null);
    List<Comment> commentList = new ArrayList<>();
    commentList.add(new Comment(1, "Good book!", book));
    commentList.add(new Comment(2, "Bad book!",book));

    return commentList;
  }
}