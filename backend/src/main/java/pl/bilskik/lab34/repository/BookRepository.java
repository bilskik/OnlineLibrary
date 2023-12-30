package pl.bilskik.lab34.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bilskik.lab34.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
