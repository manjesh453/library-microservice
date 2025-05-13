package com.bookservice.service;

import com.bookservice.dto.BookRequestDto;
import com.bookservice.dto.BookResponseDto;

import java.util.List;

public interface BookService {

    String createBook(BookRequestDto book);
    String updateBook(BookRequestDto book,String id);
    String deleteBook(String id);
    List<BookResponseDto> getAllBooks();
    BookResponseDto getBookById(String id);
    List<BookResponseDto> getBooksByAuthor(String author);
    List<BookResponseDto> getBooksByStatus(String status);
    String borrowBook(String bookId);
    String returnBook(String bookId);
}
