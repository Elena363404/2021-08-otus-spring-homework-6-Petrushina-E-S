package ru.otus.elena363404.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.elena363404.dao.*;
import ru.otus.elena363404.domain.Author;
import ru.otus.elena363404.domain.Book;
import ru.otus.elena363404.domain.Genre;

import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceByInput implements BookService {

  private final IOService ioService;
  private final BookDao bookDao;
  private final AuthorDao authorDao;
  private final GenreDao genreDao;

  @Override
  @Transactional
  public void createBook() {

    ioService.out("Input name for the book: \n");
    String nameBook = ioService.readString();
    ioService.out("Input id Author for the book from List: \n" + authorDao.getAllAuthor());
    long author_id = ioService.getInputId();
    Author author = authorDao.getAuthorById(author_id).stream().findFirst().orElse(null);
    ioService.out("Input id Genre for the book from List: \n" + genreDao.getAllGenre());
    long genre_id = ioService.getInputId();
    Genre genre = genreDao.getGenreById(genre_id).stream().findFirst().orElse(null);
    Book book = new Book(0, nameBook, author, genre, null);

    bookDao.createBook(book);
  }

  @Override
  @Transactional
  public void updateBook() {
    ioService.out("Input id of the book for update: \n");
    long id = ioService.getInputId();
    ioService.out("Input a new name for the book: \n");
    String name = ioService.readString();
    ioService.out("Input id Author for the book from List: \n" + authorDao.getAllAuthor());
    long author_id = ioService.getInputId();
    Author author = authorDao.getAuthorById(author_id).stream().findFirst().orElse(null);
    ioService.out("Input id Genre for the book from List: \n" + genreDao.getAllGenre());
    long genre_id = ioService.getInputId();
    Genre genre = genreDao.getGenreById(genre_id).stream().findFirst().orElse(null);
    Book book = new Book(0, name, author, genre, null);
    bookDao.updateBook(book);
  }

  @Override
  @Transactional
  public void deleteBook() {
    ioService.out("Input id of the book for delete: \n");
    long id = ioService.getInputId();
    bookDao.deleteBookById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public void getBookById() {
    ioService.out("Input id of the book: \n");
    long id = ioService.getInputId();
    ioService.out(bookDao.getBookById(id) == null ? "Book with input ID not found!" : "\n" + bookDao.getBookById(id));
  }

  @Override
  @Transactional(readOnly = true)
  public void getAllBook() {
    ioService.out("All books: \n" + bookDao.getAllBook().stream().map(Book::toString).collect(Collectors.joining("\n")));
  }
}
