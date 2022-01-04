package ru.otus.elena363404.dao;

import org.springframework.stereotype.Repository;
import ru.otus.elena363404.domain.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class BookDaoJpa implements BookDao {

    @PersistenceContext
    private final EntityManager em;

    public BookDaoJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public void saveBook(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
        } else {
            em.merge(book);
        }
    }

    @Override
    public Optional<Book> getBookById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public void deleteBookById(long id) {
        Query query = em.createQuery("delete " +
          "from Book b " +
          "where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Book> getAllBookByAuthorId(long author_id) {
        TypedQuery<Book> query = em.createQuery("select b " +
            "from Book b " +
            "where b.author_id = :author_id",
          Book.class);
        query.setParameter("author_id", author_id);
        return query.getResultList();
    }

    @Override
    public List<Book> getAllBook() {
        EntityGraph<?> entityGraph = em.getEntityGraph("author-genre-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }
}
