package com.borrowservice.repo;

import com.borrowservice.entity.Borrow;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BorrowRepo extends MongoRepository<Borrow, String> {
    List<Borrow> findByUserId(String userId);
    List<Borrow> findByCreatedDateBetween(Date startDate, Date endDate);
    List<Borrow> findByApprovedBy(String id);
    Borrow findByUserIdAndBookId(String userId, String bookId);
}
