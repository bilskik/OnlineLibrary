package pl.bilskik.lab34.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bilskik.lab34.entity.Book;
import pl.bilskik.lab34.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("${api.books}")
public class BookController {

    @Autowired
    public BookService bookService;

    @GetMapping("${books.bookId}")
    public  ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
        return new ResponseEntity<Book>(bookService.getBookById(bookId), HttpStatusCode.valueOf(200));
    }
    @GetMapping()
    public  ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<List<Book>>(bookService.getAllBooks(), HttpStatusCode.valueOf(200));
    }
    @PostMapping
    public  ResponseEntity<Book> saveBook(@RequestBody Book book) {
        return new ResponseEntity<Book>(bookService.saveBook(book), HttpStatusCode.valueOf(201));
    }
    @PutMapping
    public  ResponseEntity<Book> updateBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.updateBook(book), HttpStatusCode.valueOf(204));
    }
    @DeleteMapping("${books.bookId}")
    public  ResponseEntity<Void> deleteBookById(@PathVariable long bookId) {
        System.out.println(bookId);
        return new ResponseEntity<Void>(bookService.deleteBookById(bookId), HttpStatusCode.valueOf(200));
    }


}
