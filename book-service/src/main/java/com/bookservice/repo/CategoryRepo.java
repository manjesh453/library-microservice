package com.bookservice.repo;

import com.bookservice.entity.Category;
import com.bookservice.shared.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends MongoRepository<Category, String> {
    List<Category> findByStatus(Status status);
}
