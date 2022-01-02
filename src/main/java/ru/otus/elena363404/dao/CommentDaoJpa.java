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

  public CommentDaoJpa(EntityManager em) {
    this.em = em;
  }

  @Override
  public void createComment(Comment comment) {
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
    TypedQuery<Comment> query = em.createQuery("select s from Comment s where book_id = :book_id", Comment.class);
    query.setParameter("book_id", id);
    return query.getResultList();
  }

  @Override
  public void updateComment(Comment comment) {
    Query query = em.createQuery("update Comment s " +
      "set s.comment = :comment " +
      "where s.id = :id");
    query.setParameter("comment", comment.getComment());
    query.setParameter("id", comment.getId());
    query.executeUpdate();
  }

  @Override
  public void deleteCommentById(long id) {
    Query query = em.createQuery("delete " +
      "from Comment s " +
      "where s.id = :id");
    query.setParameter("id", id);
    query.executeUpdate();
  }

  @Override
  public List<Comment> getAllComment() {
    TypedQuery<Comment> query = em.createQuery("select s from Comment s", Comment.class);
    return query.getResultList();
  }
}
