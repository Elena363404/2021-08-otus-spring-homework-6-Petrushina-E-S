package ru.otus.elena363404.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedEntityGraph(name = "author-genre-entity-graph",
  attributeNodes = {@NamedAttributeNode("author"), @NamedAttributeNode("genre")})
@Table(name = "book")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Fetch(FetchMode.SELECT)
  @BatchSize(size = 5)
  @ManyToOne(targetEntity = Author.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "BOOK_AUTHOR_FK"))
  private Author author;

  @Fetch(FetchMode.SELECT)
  @BatchSize(size = 5)
  @ManyToOne(targetEntity = Genre.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "genre_id", foreignKey = @ForeignKey(name = "BOOK_GENRE_FK"))
  private Genre genre;

  @Fetch(FetchMode.SELECT)
  @BatchSize(size = 5)
  @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "book_id")
  private List<Comment> comment;

}

