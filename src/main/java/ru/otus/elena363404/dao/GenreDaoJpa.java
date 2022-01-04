package ru.otus.elena363404.dao;

import org.springframework.stereotype.Repository;
import ru.otus.elena363404.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreDaoJpa implements GenreDao {

  @PersistenceContext
  private final EntityManager em;

  public GenreDaoJpa(EntityManager em) {
    this.em = em;
  }

  @Override
  public void saveGenre(Genre genre) {
    if (genre.getId() == 0) {
      em.persist(genre);
    } else {
      em.merge(genre);
    }
  }

  @Override
  public Optional<Genre> getGenreById(long id) {
    return Optional.ofNullable(em.find(Genre.class, id));
  }

  @Override
  public void deleteGenreById(long id) {
    Query query = em.createQuery("delete " +
      "from Genre s " +
      "where s.id = :id");
    query.setParameter("id", id);
    query.executeUpdate();
  }

  @Override
  public List<Genre> getAllGenre() {
    TypedQuery<Genre> query = em.createQuery("select s from Genre s", Genre.class);
    return query.getResultList();
  }

}
