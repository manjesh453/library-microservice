package com.users.service.impl;

import com.users.entity.RefreshToken;
import com.users.exception.DataNotFoundException;
import com.users.repo.RefreshRepo;
import com.users.repo.UserRepo;
import com.users.service.RefreshService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshServiceImpl implements RefreshService {
    private final UserRepo userRepo;

    private final RefreshRepo refreshRepo;

    @Override
    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepo.findByEmail(username).orElseThrow(() -> new DataNotFoundException("User not found")))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusSeconds(86400))
                .build();
        return refreshRepo.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshRepo.delete(token);
            throw new DataNotFoundException("Need to Login");
        }
        return token;
    }
}
