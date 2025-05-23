package com.bookservice.repo;

import com.bookservice.entity.Book;
import com.bookservice.shared.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends MongoRepository<Book, String> {

    List<Book> findByAuthorName(String author);

    List<Book> findByStatus(Status status);

    Integer countByStatus(Status status);

    @Query("{ 'title': { $regex: ?0, $options: 'i' } }")
    List<Book> findByTitle(String title);

    List<Book> findByStatus(Status status, Pageable pageable);
}
