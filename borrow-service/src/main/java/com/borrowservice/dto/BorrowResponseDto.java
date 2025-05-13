package com.borrowservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BorrowResponseDto {
    private String id;
    private String bookId;
    private String userId;
    private Date borrowDate;
    private String status;
}
