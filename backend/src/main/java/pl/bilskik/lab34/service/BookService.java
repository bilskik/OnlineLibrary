package pl.bilskik.lab34.service;
import org.springframework.stereotype.Service;
import pl.bilskik.lab34.entity.Book;
import pl.bilskik.lab34.repository.BookRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BookService {

    private BookRepository bookRepository;
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public Book getBookById(Long bookId) {
        if(bookId != null) {
            Optional<Book> book = bookRepository.findById(bookId);
            if(book.isPresent()) {
                return book.get();
            } else {
                throw new NoSuchElementException("Book with givenId doesnt exist!");
            }
        } else {
            throw new NoSuchElementException("ProductId is Null!");
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        if(books == null) {
            throw new NoSuchElementException("There is no products in DB!");
        }
        return books;
    }

    public Book saveBook(Book book) {
        if(book != null) {
            bookRepository.save(book);
            return book;
        } else {
            throw new NoSuchElementException("book is null!");
        }
    }

    public Book updateBook(Book book) {
        if(book != null) {
            bookRepository.save(book);
            return book;
        } else {
            throw new NoSuchElementException("book is null!");
        }
    }

    public Void deleteBookById(long bookId) {
        bookRepository.deleteById(bookId);
        return null;
    }
}
