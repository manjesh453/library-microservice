package com.users.service;

public interface BorrowBookService {
    String borrowBook(String bookId,String userId);

    String returnBook(String bookId,String userId);
}
