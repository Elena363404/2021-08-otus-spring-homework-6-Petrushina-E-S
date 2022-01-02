package ru.otus.elena363404.dao;

import ru.otus.elena363404.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {

  void createComment(Comment comment);

  Optional<Comment> getCommentById(long id);

  List<Comment> getAllCommentByBookId(long id);

  void updateComment(Comment comment);

  void deleteCommentById(long id);

  List<Comment> getAllComment();
}
