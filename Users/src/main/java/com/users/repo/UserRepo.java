package com.users.repo;

import com.users.entity.Users;
import com.users.shared.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<Users, String> {

    @Query("{ 'email' : ?0 }")
    Optional<Users> findByEmail(@Param("email") String email);

    List<Users> findByStatus(Status status);

    Users findByVerificationCode(String verificationCode);

    @Query("{ 'createdDate' : { $gte: ?0, $lte: ?1 } }")
    List<Users>findByCreatedDateBetween(Date startDate, Date endDate);

    long count();

    Integer countByStatus(Status status);
}
