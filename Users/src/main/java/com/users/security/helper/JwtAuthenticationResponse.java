package com.users.security.helper;

import com.users.shared.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;
    private String userId;
    private String userName;
    private Role role;
}
