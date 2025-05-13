package com.users.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class BorrowRequestDto {
    private String bookId;
    private String userId;
    private Date borrowDate;
}
