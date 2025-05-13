package com.borrowservice.controller;

import com.borrowservice.dto.BorrowRequestDto;
import com.borrowservice.dto.BorrowResponseDto;
import com.borrowservice.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/borrow/")
public class BorrowController {
    private final BorrowService borrowService;

    @PostMapping("/add")
    public String borrowBook(@RequestBody BorrowRequestDto requestDto){
        return borrowService.borrowBook(requestDto);
    }

    @PostMapping("/updateBorrow/{id}")
    public String updateBorrowBook(@RequestBody Date requestDto,@PathVariable String id){
        return borrowService.updateBorrowBook(requestDto,id);
    }

    @GetMapping("/delete/{id}")
    public String deleteBorrowBook(@PathVariable String id){
        return borrowService.deleteBorrowBook(id);
    }

    @GetMapping("/getById/{id}")
    public BorrowResponseDto getBorrowBook(@PathVariable String id){
        return borrowService.getBorrowBook(id);
    }

    @GetMapping("/getAll")
    public List<BorrowResponseDto> getAllBorrowBook(){
        return borrowService.getBorrowedBooks();
    }

    @PostMapping("/getBorrowBooksByDate")
    public List<BorrowResponseDto> getBorrowedBooksByDate(@RequestBody Date from, @RequestBody Date to){
        return borrowService.getBorrowedBookByDate(from, to);
    }

    @GetMapping("/getBorrowDetailByUserId/{userId}")
    public List<BorrowResponseDto> getBorrowedByUserId(@PathVariable String userId){
        return borrowService.getBorrowedByUserId(userId);
    }

    @PostMapping("/admin/approveBorrowBook")
    public String approveBook(@RequestBody String userId,@RequestBody String borrowId){
        return borrowService.approveBorrowBook(userId,borrowId);
    }

    @GetMapping("/admin/bookApprovedByApprover/{approveId}")
    public List<BorrowResponseDto> getApprovedBorrowBookByApproveId(@PathVariable String approveId){
        return borrowService.getBorrowedBooksByApprovedAdmin(approveId);
    }

    @PostMapping("/returnBorrowBook/{userId}")
    public String returnBorrowBook(@PathVariable String userId,@RequestBody String bookId){
        return borrowService.returnBorrowBook(userId,bookId);
    }
}
