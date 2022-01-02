package ru.otus.elena363404.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.elena363404.dao.AuthorDao;
import ru.otus.elena363404.domain.Author;

import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceByInput implements AuthorService{

  private final IOService ioService;
  private final AuthorDao authorDao;

  @Override
  @Transactional
  public void createAuthor() {
    ioService.out("Input name for the author: \n");
    String nameAuthor = ioService.readString();
    Author author = new Author(0, nameAuthor);
    authorDao.createAuthor(author);
  }

  @Override
  @Transactional
  public void updateAuthor() {
    ioService.out("Input id of the author for update: \n");
    ioService.out("Input a new name for the author: \n");
    String name = ioService.readString();
    Author author = new Author(0, name);
    authorDao.updateAuthor(author);
  }

  @Override
  @Transactional
  public void deleteAuthor() {
    ioService.out("Input id of the author for delete: \n");
    long id = ioService.getInputId();
    authorDao.deleteAuthorById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public void getAuthorById() {
    ioService.out("Input id of the author: \n");
    long id = ioService.getInputId();

    ioService.out(!authorDao.getAuthorById(id).isPresent() ? "Author with input ID not found!" : "\n" + authorDao.getAuthorById(id));
  }

  @Override
  @Transactional(readOnly = true)
  public void getAllAuthor() {
    ioService.out("All authors: \n" + authorDao.getAllAuthor().stream().map(Author::toString).collect(Collectors.joining("\n")));
  }
}
