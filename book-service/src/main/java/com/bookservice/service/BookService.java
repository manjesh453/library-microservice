package com.bookservice.service;

import com.bookservice.dto.BookRequestDto;
import com.bookservice.dto.BookResponseDto;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface BookService {

    String createBook(BookRequestDto book, String fileName);
    String updateBook(BookRequestDto book,String id, String fileName) throws IOException;
    String deleteBook(String id);
    List<BookResponseDto> getAllBooks();
    BookResponseDto getBookById(String id);
    List<BookResponseDto> getBooksByAuthor(String author);
    List<BookResponseDto> getBooksByStatus(String status);
    String borrowBook(String bookId);
    String returnBook(String bookId);
    Map<String,Integer> countBook();
    List<BookResponseDto> searchByTitle(String title);
}
