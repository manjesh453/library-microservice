package com.bookservice.repo;

import com.bookservice.entity.Book;
import com.bookservice.shared.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends MongoRepository<Book, String> {

    List<Book> findByAuthorName(String author);
    List<Book> findByStatus(Status status);
    Integer countByStatus(Status status);
    List<Book> findByTitle(String title);
}
