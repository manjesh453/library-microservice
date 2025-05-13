package com.users.service.impl;

import com.users.dto.BorrowRequestDto;
import com.users.dto.UserResponseDto;
import com.users.service.BorrowBookService;
import com.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowBookService {

    private final RestTemplate restTemplate;
    private final UserService userService;

    @Value("${borrow.url}")
    private String borrowUrl;

    @Value("${book.url}")
    private String bookUrl;

    @Override
    public String borrowBook(String bookId, String userId) {
        String bookAvailability = checkBookAvailability(bookId);
        if (bookAvailability == null || bookAvailability.equalsIgnoreCase("Sorry book not available")) {
            return bookAvailability;
        }
        UserResponseDto user = userService.getUserById(userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getJwtFromContext());
        headers.setContentType(MediaType.APPLICATION_JSON);

        BorrowRequestDto requestDto = BorrowRequestDto.builder()
                .bookId(bookId)
                .borrowDate(new Date())
                .userId(user.getId())
                .build();

        HttpEntity<BorrowRequestDto> entity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                borrowUrl+"/add", HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            return "Sorry you cannot borrow your book";
        }

        return "Book borrowed successfully!";
    }

    @Override
    public String returnBook(String bookId, String userId) {
        String bookAvailability = returnBookByUser(userId,bookId);
        if (bookAvailability == null || bookAvailability.equalsIgnoreCase("Sorry cannot remove your book")) {
            return bookAvailability;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getJwtFromContext());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                bookUrl + "/returnBook/" + bookId, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            return "Sorry cannot remove your book";
        }

        return response.getBody();
    }

    private String returnBookByUser(String userId,String bookId){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getJwtFromContext());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(bookId,headers);

        ResponseEntity<String> response = restTemplate.exchange(
                borrowUrl + "/returnBorrowBook/" + userId, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            return "Sorry cannot remove your book";
        }

        return response.getBody();
    }

    private String checkBookAvailability(String bookId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getJwtFromContext());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                bookUrl + "/borrowBook/" + bookId, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            return "Sorry book not available";
        }

        return response.getBody();
    }

    public String getJwtFromContext() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getDetails() instanceof String jwt) {
            return jwt;
        }
        return null;
    }
}
