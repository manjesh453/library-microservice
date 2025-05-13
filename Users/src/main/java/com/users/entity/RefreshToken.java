package com.users.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {
    @Id
    private String id;
    private String token;
    private Instant expiryDate;
    private Users user;
}
