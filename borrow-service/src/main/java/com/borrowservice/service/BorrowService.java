package com.borrowservice.service;

import com.borrowservice.dto.BorrowRequestDto;
import com.borrowservice.dto.BorrowResponseDto;

import java.util.Date;
import java.util.List;

public interface BorrowService {
    String borrowBook(BorrowRequestDto requestDto);
    String updateBorrowBook(Date requestDto,String Id);
    String deleteBorrowBook(String Id);
    BorrowResponseDto getBorrowBook(String Id);
    List<BorrowResponseDto> getBorrowedBooks();
    List<BorrowResponseDto> getBorrowedByUserId(String userId);
    List<BorrowResponseDto> getBorrowedBookByDate(Date from, Date to);
    String approveBorrowBook(String Id,String bookId);
    List<BorrowResponseDto> getBorrowedBooksByApprovedAdmin(String userId);
    String returnBorrowBook(String Id,String bookId);

}
