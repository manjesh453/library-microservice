package com.borrowservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BorrowRequestDto {
    private String bookId;
    private String userId;
    private Date borrowDate;
}
