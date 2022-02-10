package ru.otus.elena363404.dao;

import ru.otus.elena363404.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {

  void saveComment(Comment comment);

  Optional<Comment> getCommentById(long id);

  List<Comment> getAllCommentByBookId(long id);

  void deleteCommentById(long id);

  List<Comment> getAllComment();
}
