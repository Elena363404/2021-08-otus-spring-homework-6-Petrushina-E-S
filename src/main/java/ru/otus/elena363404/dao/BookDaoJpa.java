package ru.otus.elena363404.dao;

import org.springframework.stereotype.Repository;
import ru.otus.elena363404.domain.Author;
import ru.otus.elena363404.domain.Book;
import ru.otus.elena363404.domain.Genre;

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
    public void createBook(Book book) {
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
    public void updateBook(Book book) {
        Author author = book.getAuthor();
        Genre genre = book.getGenre();
        Query query = em.createQuery("update Book s set s.name = :name, s.author_id = :author_id, s.genre_id = :genre_id where s.id = :id");
        query.setParameter("name", book.getName());
        query.setParameter("author_id", author.getId());
        query.setParameter("genre_id", genre.getId());
        query.executeUpdate();
    }

    @Override
    public void deleteBookById(long id) {
        Query query = em.createQuery("delete " +
          "from Book s " +
          "where s.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Book> getAllBookByAuthorId(long author_id) {
        TypedQuery<Book> query = em.createQuery("select s " +
            "from Book s " +
            "where s.author_id = :author_id",
          Book.class);
        query.setParameter("author_id", author_id);
        return query.getResultList();
    }

    @Override
    public List<Book> getAllBook() {
        EntityGraph<?> entityGraph = em.getEntityGraph("author-genre-entity-graph");
        TypedQuery<Book> query = em.createQuery("select s from Book s left join fetch s.comment", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }
}
