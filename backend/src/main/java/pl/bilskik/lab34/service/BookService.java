package pl.bilskik.lab34.service;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.bilskik.lab34.data.BookDTO;
import pl.bilskik.lab34.entity.Book;
import pl.bilskik.lab34.repository.BookRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public BookService(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("There is no book with this ID!"));
    }

    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books;
    }

    public String saveBook(BookDTO bookdto) {
        Book book = modelMapper.map(bookdto, Book.class);
        if(book != null) {
            bookRepository.save(book);
            return "SAVED";
        } else {
            throw new NoSuchElementException("book is null!");
        }
    }

    public String updateBook(Book book) {
        if(book != null) {
            bookRepository.save(book);
            return "UPDATED";
        } else {
            throw new NoSuchElementException("book is null!");
        }
    }

    public String deleteBookById(long bookId) {
        bookRepository.deleteById(bookId);
        return "DELETED";
    }
}
