package com.bookservice.service.impl;

import com.bookservice.dto.BookRequestDto;
import com.bookservice.dto.BookResponseDto;
import com.bookservice.entity.Book;
import com.bookservice.entity.Category;
import com.bookservice.exception.DataNotFoundException;
import com.bookservice.exception.ResourceNotFoundException;
import com.bookservice.repo.BookRepo;
import com.bookservice.service.BookService;
import com.bookservice.service.FileService;
import com.bookservice.shared.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepo bookRepo;
    private final ModelMapper modelMapper;
    private final FileService fileService;
    @Value("${project.image}")
    private String path;
    @Override
    public String createBook(BookRequestDto book, String fileName) {
        Book bookEntity = modelMapper.map(book, Book.class);
        bookEntity.setImageName(fileName);
        bookEntity.setStatus(Status.ACTIVE);
        bookRepo.save(bookEntity);
        return "Book has been added successfully";
    }

    @Override
    public String updateBook(BookRequestDto book, String id, String fileName) throws IOException {
        Book bookEntity = getAlreadyExistsBookById(id);
        fileService.deleteImage(path,bookEntity.getImageName());
        bookEntity.setImageName(fileName);
        bookEntity.setTitle(book.getTitle());
        bookEntity.setDescription(book.getDescription());
        bookEntity.setNumberOfBooks(book.getNumberOfBooks());
        bookEntity.setAuthorName(book.getAuthorName());
        bookRepo.save(bookEntity);
        return "Book detail has been updated successfully";
    }

    @Override
    public String deleteBook(String id) {
        Book bookEntity = getAlreadyExistsBookById(id);
        bookEntity.setStatus(Status.DELETED);
        bookRepo.save(bookEntity);
        return "Book has been deleted successfully";
    }

    @Override
    public List<BookResponseDto> getAllBooks() {
        List<Book> books = bookRepo.findAll();
        return books.stream().map(li->modelMapper.map(li, BookResponseDto.class)).toList();
    }

    @Override
    public BookResponseDto getBookById(String id) {
        return modelMapper.map(getAlreadyExistsBookById(id), BookResponseDto.class);
    }

    @Override
    public List<BookResponseDto> getBooksByAuthor(String author) {
        List<Book> books = bookRepo.findByAuthorName(author);
        return books.stream().map(li->modelMapper.map(li, BookResponseDto.class)).toList();
    }

    @Override
    public List<BookResponseDto> getBooksByStatus(String status) {
        List<Book> books = bookRepo.findByStatus(Status.valueOf(status));
        return books.stream().map(li->modelMapper.map(li, BookResponseDto.class)).toList();
    }

    @Override
    public String borrowBook(String bookId) {
        Book book=getAlreadyExistsBookById(bookId);
        if(book.getStatus().equals(Status.ACTIVE) && book.getNumberOfBooks()>0){
            book.setNumberOfBooks(book.getNumberOfBooks() - 1);
            bookRepo.save(book);
            return "Book has been borrowed successfully";
        }
        return "Sorry no available of book";
    }

    @Override
    public String returnBook(String bookId) {
        Book book=getAlreadyExistsBookById(bookId);
        book.setNumberOfBooks(book.getNumberOfBooks() + 1);
        bookRepo.save(book);
        return "Book has been return successfully";
    }

    private Book getAlreadyExistsBookById(String id) {
        return bookRepo.findById(id).orElseThrow(() ->
        {
            log.error("Category with id {} not found", id);
            return new DataNotFoundException("Category not found");
        });
    }

}
