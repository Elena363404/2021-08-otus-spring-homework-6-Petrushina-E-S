package ru.otus.elena363404.dao;

import org.springframework.stereotype.Repository;
import ru.otus.elena363404.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentDaoJpa implements CommentDao {

  @PersistenceContext
  private final EntityManager em;

  private final BookDao bookDao;

  public CommentDaoJpa(EntityManager em, BookDao bookDao) {
    this.em = em;
    this.bookDao = bookDao;
  }

  @Override
  public void saveComment(Comment comment) {
    if (comment.getId() == 0) {
      em.persist(comment);
    } else {
      em.merge(comment);
    }
  }

  @Override
  public Optional<Comment> getCommentById(long id) {
    return Optional.ofNullable(em.find(Comment.class, id));
  }

  @Override
  public List<Comment> getAllCommentByBookId(long id) {
    TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.book = :book", Comment.class);
    query.setParameter("book", bookDao.getBookById(id).stream().findFirst().orElse(null));
    return query.getResultList();
  }

  @Override
  public void deleteCommentById(long id) {
    Query query = em.createQuery("delete " +
      "from Comment c " +
      "where c.id = :id");
    query.setParameter("id", id);
    query.executeUpdate();
  }

  @Override
  public List<Comment> getAllComment() {
    TypedQuery<Comment> query = em.createQuery("select c from Comment c", Comment.class);
    return query.getResultList();
  }
}
