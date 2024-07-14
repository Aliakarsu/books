package com.example.bookapp.controller;

import com.example.bookapp.dtos.BookDto;
import com.example.bookapp.model.Book;
import com.example.bookapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Book getBookById(@PathVariable(name = "id") long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable(name = "id") long id, @RequestBody BookDto bookDto) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            System.out.println(optionalBook);
            return ResponseEntity.ok(optionalBook.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable(name = "id") long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            System.out.println("id number "+id+" : Deleted");
            return ResponseEntity.ok("id number "+id+": Deleted");
        }
        else {
            return ResponseEntity.ok("Not found");
        }
    }

}
