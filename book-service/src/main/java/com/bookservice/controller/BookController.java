package com.bookservice.controller;

import com.bookservice.dto.BookRequestDto;
import com.bookservice.dto.BookResponseDto;
import com.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/admin/add")
    public String addBook(@RequestBody BookRequestDto book) {
        return bookService.createBook(book);
    }

    @PostMapping("/update/{id}")
    public String updateBook(@RequestBody BookRequestDto book, @PathVariable String id) {
        return bookService.updateBook(book, id);
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteBook(@PathVariable String id) {
        return bookService.deleteBook(id);
    }

    @GetMapping("/getAllBooks")
    public List<BookResponseDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/getBookByAuthor/{author}")
    public List<BookResponseDto> getBooksByAuthor(@PathVariable String author) {
        return bookService.getBooksByAuthor(author);
    }

    @GetMapping("/getBookById/{id}")
    public BookResponseDto getBookById(@PathVariable String id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/getBookByStatus/{status}")
    public List<BookResponseDto> getBookByStatus(@PathVariable String status) {
        return bookService.getBooksByStatus(status);
    }

    @GetMapping("/borrowBook/{bookId}")
    public String borrowBook(@PathVariable String bookId){
        return bookService.borrowBook(bookId);
    }

    @GetMapping("/returnBook/{bookId}")
    public String returnBook(@PathVariable String bookId){
        return bookService.returnBook(bookId);
    }
}
