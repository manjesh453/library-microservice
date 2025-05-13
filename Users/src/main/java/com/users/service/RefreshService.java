package com.users.service;

import com.users.entity.RefreshToken;

public interface RefreshService {
    RefreshToken createRefreshToken(String username);

    RefreshToken verifyExpiration(RefreshToken token);
}
