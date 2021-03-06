package ru.otus.elena363404.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.elena363404.dao.BookDao;
import ru.otus.elena363404.dao.CommentDao;
import ru.otus.elena363404.domain.Book;
import ru.otus.elena363404.domain.Comment;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceByInput implements CommentService {

  private final IOService ioService;
  private final CommentDao commentDao;
  private final BookDao bookDao;


  @Override
  @Transactional
  public void createComment() {
    ioService.out("Input comment: \n");
    String txtComment = ioService.readString();

    ioService.out("Input a book ID for comment: \n" + bookDao.getAllBook());
    long idBook = ioService.getInputId();
    Book book = bookDao.getBookById(idBook).stream().findFirst().orElse(null);

    Comment comment = new Comment(0, txtComment, book);
    commentDao.saveComment(comment);
  }

  @Override
  @Transactional
  public void updateComment() {
    ioService.out("Input id of the comment for update: \n");
    long id = ioService.getInputId();
    ioService.out("Input a new comment: \n");
    String txtComment = ioService.readString();

    ioService.out("Input a book ID for comment: \n" + bookDao.getAllBook());
    long idBook = ioService.getInputId();
    Book book = bookDao.getBookById(idBook).stream().findFirst().orElse(null);

    Comment comment = new Comment(id, txtComment, book);
    commentDao.saveComment(comment);
  }

  @Override
  @Transactional
  public void deleteComment() {
    ioService.out("Input id of the comment for delete: \n");
    long id = ioService.getInputId();
    commentDao.deleteCommentById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public void getCommentById() {
    ioService.out("Input id of the comment: \n");
    long id = ioService.getInputId();
    ioService.out(commentDao.getCommentById(id)
      .map(a -> "\n" + a)
      .orElse("Comment with input ID not found!")
    );
  }

  @Override
  @Transactional(readOnly = true)
  public void getCommentByBookId() {
    ioService.out("Input book id for getting list of comments: \n");
    long id = ioService.getInputId();
    List<Comment> commentList = commentDao.getAllCommentByBookId(id);
    for (int i = 0; i < commentList.size(); i++) {
      ioService.out(commentList.get(i) + "\n");
    }
  }

  @Override
  @Transactional(readOnly = true)
  public void getAllComment() {
    ioService.out("All comments: \n" + commentDao.getAllComment().stream().map(Comment::toString).collect(Collectors.joining("\n")));
  }
}
