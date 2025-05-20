package com.borrowservice.service.impl;

import com.borrowservice.dto.BorrowRequestDto;
import com.borrowservice.dto.BorrowResponseDto;
import com.borrowservice.entity.Borrow;
import com.borrowservice.exception.ResourceNotFoundException;
import com.borrowservice.repo.BorrowRepo;
import com.borrowservice.service.BorrowService;
import com.borrowservice.shared.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowService {
    private final BorrowRepo borrowRepo;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    @Override
    public String borrowBook(BorrowRequestDto requestDto) {
        Borrow borrow = objectMapper.convertValue(requestDto, Borrow.class);
        borrow.setStatus(Status.ACTIVE);
        borrowRepo.save(borrow);
        return "You have successfully borrowed the book!";
    }

    @Override
    public String updateBorrowBook(Date requestDto, String Id) {
        Borrow borrow = getBorrowById(Id);
        borrow.setBorrowDate(requestDto);
        borrowRepo.save(borrow);
        return "You have successfully updated borrowed date of the book!";
    }

    @Override
    public String deleteBorrowBook(String Id) {
        Borrow borrow = getBorrowById(Id);
        borrow.setStatus(Status.DELETED);
        borrowRepo.save(borrow);
        return "You have successfully deleted borrowed book!";
    }

    @Override
    public BorrowResponseDto getBorrowBook(String Id) {
        return modelMapper.map(getBorrowById(Id), BorrowResponseDto.class);
    }

    @Override
    public List<BorrowResponseDto> getBorrowedBooks() {
        List<Borrow> borrows = borrowRepo.findAll();
        return borrows.stream().map(li -> modelMapper.map(li, BorrowResponseDto.class)).toList();
    }

    @Override
    public List<BorrowResponseDto> getBorrowedByUserId(String userId) {
        List<Borrow> borrows = borrowRepo.findByUserId(userId);
        return borrows.stream().map(li -> modelMapper.map(li, BorrowResponseDto.class)).toList();
    }

    @Override
    public List<BorrowResponseDto> getBorrowedBookByDate(Date from, Date to) {
        List<Borrow> borrows = borrowRepo.findByCreatedDateBetween(from, to);
        return borrows.stream().map(li -> modelMapper.map(li, BorrowResponseDto.class)).toList();
    }

    @Override
    public String approveBorrowBook(String Id,String bookId) {
        Borrow borrow=getBorrowById(bookId);
        borrow.setApprovedBy(Id);
        borrowRepo.save(borrow);
        return "You have successfully approved the book!";
    }

    @Override
    public List<BorrowResponseDto> getBorrowedBooksByApprovedAdmin(String approvedBy) {
        List<Borrow> borrows = borrowRepo.findByApprovedBy(approvedBy);
        return borrows.stream().map(li -> modelMapper.map(li, BorrowResponseDto.class)).toList();

    }

    @Override
    public String returnBorrowBook(String Id,String bookId) {
        Borrow borrow=borrowRepo.findByUserIdAndBookId(Id, bookId);
        borrow.setReturnDate(new Date());
        borrowRepo.save(borrow);
        return "You have successfully returned borrowed date of the book!";
    }

    private Borrow getBorrowById(String id) {
        return borrowRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "borrowId", id));
    }
}
