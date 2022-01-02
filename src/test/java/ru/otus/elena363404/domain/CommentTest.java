package ru.otus.elena363404.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Test Comment")
class CommentTest {

  @MockBean
  Shell shell;

  @DisplayName("Create Comment by constructor")
  @Test
  void shouldHaveCorrectConstructor() {
    Comment comment = new Comment(7, "Bad book!", 2);

    assertEquals(7, comment.getId());
    assertEquals("Bad book!", comment.getComment());
  }

}