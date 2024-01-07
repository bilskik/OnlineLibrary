package pl.bilskik.lab34.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bilskik.lab34.data.BookDTO;
import pl.bilskik.lab34.data.ResponseMessage;
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
        return new ResponseEntity<>(bookService.getBookById(bookId), HttpStatus.OK);
    }
    @GetMapping()
    public  ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }
    @PostMapping
    public  ResponseEntity<ResponseMessage> saveBook(@RequestBody @Valid BookDTO book) {
        return new ResponseEntity<>(new ResponseMessage(bookService.saveBook(book)), HttpStatus.CREATED);
    }
    @PutMapping
    public  ResponseEntity<ResponseMessage> updateBook(@RequestBody @Valid BookDTO book) {
        return new ResponseEntity<>(new ResponseMessage(bookService.updateBook(book)), HttpStatus.OK);
    }
    @DeleteMapping("${books.bookId}")
    public  ResponseEntity<ResponseMessage> deleteBookById(@PathVariable long bookId) {
        return new ResponseEntity<>(new ResponseMessage(bookService.deleteBookById(bookId)), HttpStatus.OK);
    }


}
