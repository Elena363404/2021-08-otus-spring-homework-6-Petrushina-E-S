package ru.otus.elena363404.dao;

import org.springframework.stereotype.Repository;
import ru.otus.elena363404.domain.Author;

import javax.persistence.*;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorDaoJpa implements AuthorDao {

  @PersistenceContext
  private final EntityManager em;

  public AuthorDaoJpa(EntityManager em) {
    this.em = em;
  }

  @Override
  public void createAuthor(Author author) {
    if (author.getId() == 0) {
      em.persist(author);
    } else {
      em.merge(author);
    }
  }

  @Override
  public Optional<Author> getAuthorById(long id) {
    return Optional.ofNullable(em.find(Author.class, id));
  }

  @Override
  public void updateAuthor(Author author) {
    Query query = em.createQuery("update Author s " +
      "set s.name = :name " +
      "where s.id = :id");
    query.setParameter("name", author.getName());
    query.setParameter("id", author.getId());
    query.executeUpdate();
  }

  @Override
  public void deleteAuthorById(long id) {
    Query query = em.createQuery("delete " +
      "from Author s " +
      "where s.id = :id");
    query.setParameter("id", id);
    query.executeUpdate();
  }


  @Override
  public List<Author> getAllAuthor() {
    TypedQuery<Author> query = em.createQuery("select s from Author s", Author.class);
    return query.getResultList();
  }
}
