package ru.otus.elena363404.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@DisplayName("Test Book")
class BookTest {

  @MockBean
  Shell shell;

  @DisplayName("Create Book by constructor")
  @Test
  void shouldHaveCorrectConstructor() {
    List<Comment> commentList = new ArrayList<>();
    commentList.add( new Comment(0, "Good Book!!!", 4));
    Book book = new Book(4, "It", new Author(3, "King"), new Genre(2, "Horror"), commentList);

    assertEquals(4, book.getId());
    assertEquals("It", book.getName());
    assertEquals("King", book.getAuthor().getName());
    assertEquals("Horror", book.getGenre().getName());
    assertEquals("Good Book!!!", book.getComment().get(0).getComment());
  }
}