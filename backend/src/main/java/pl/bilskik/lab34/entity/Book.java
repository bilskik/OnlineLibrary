package pl.bilskik.lab34.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "book")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id", nullable = false)
    private Long bookId;
    @Column(name = "name")
    private String name;
    @Column(name = "author")
    private String author;
}
