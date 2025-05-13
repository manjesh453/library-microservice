package com.users.repo;

import com.users.entity.RefreshToken;
import com.users.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshRepo extends MongoRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);

    RefreshToken findByUserId(String userId);
}
