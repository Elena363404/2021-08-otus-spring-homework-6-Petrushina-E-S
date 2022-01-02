package ru.otus.elena363404.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.elena363404.domain.Author;
import ru.otus.elena363404.domain.Comment;

import static org.mockito.Mockito.*;

@DisplayName("Test CommentServiceByInput")
@SpringBootTest(properties = {
  InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
  ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class CommentServiceByInputTest {

  @MockBean
  private IOService ioService;

  @Autowired
  private CommentService commentService;

  @MockBean
  Shell shell;


  @Test
  @DisplayName("Check notification on create comment by input")
  void createCommentTest() {
    when(ioService.readString()).thenReturn("Comment for book");
    when(ioService.getInputId()).thenReturn(2L);
    commentService.createComment();
    verify(ioService, times(1)).out("Input comment: \n");
    verify(ioService, times(1)).out("Input id book for add comment: \n");
  }

  @Test
  @DisplayName("Check notification on update comment by input")
  void updateCommentTest() {
    when(ioService.readString()).thenReturn("Good book");
    when(ioService.getInputId()).thenReturn(1L);
    commentService.updateComment();
    verify(ioService, times(1)).out("Input id of the comment for update: \n");
    verify(ioService, times(1)).out("Input a new comment: \n");
  }

  @Test
  @DisplayName("Check notification on delete comment")
  void deleteCommentTest() {
    commentService.deleteComment();
    verify(ioService, times(1)).out("Input id of the comment for delete: \n");
    when(ioService.getInputId()).thenReturn(1L);
  }

  @Test
  @DisplayName("Check notification on get comment by ID")
  void getCommentByIdTest() {
    commentService.getCommentById();
    verify(ioService, times(1)).out("Input id of the comment: \n");
    when(ioService.getInputId()).thenReturn(1L);
  }
}