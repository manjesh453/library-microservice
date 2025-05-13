package com.users.controller;

import com.users.service.BorrowBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AddBookController {
    private final BorrowBookService bookService;

    @PostMapping("/borrowBook")
    public String borrowBook(@RequestBody String bookId,@RequestBody String userId) {
        return bookService.borrowBook(bookId, userId);
    }

    @PostMapping("/returnBook")
    public String returnBook(@RequestBody String bookId,@RequestBody String userId) {
        return bookService.returnBook(bookId, userId);
    }

}
