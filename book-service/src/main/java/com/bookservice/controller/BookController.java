package com.bookservice.controller;

import com.bookservice.dto.BookRequestDto;
import com.bookservice.dto.BookResponseDto;
import com.bookservice.service.BookService;
import com.bookservice.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final FileService fileService;
    @Value("${project.image}")
    private String path;

    @PostMapping("/admin/add")
    public String addBook(@RequestPart("image") MultipartFile image, @RequestPart BookRequestDto book) throws IOException {
        String filename = fileService.uploadImage(path, image);
        return bookService.createBook(book,filename);
    }

    @PostMapping("/update/{id}")
    public String updateBook(@RequestPart("image") MultipartFile image, @RequestPart BookRequestDto book, @PathVariable String id) throws IOException {
        String filename = fileService.uploadImage(path, image);
        return bookService.updateBook(book, id,filename);
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
