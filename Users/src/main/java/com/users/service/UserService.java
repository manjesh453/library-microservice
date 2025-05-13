package com.users.service;

import com.users.dto.ChangePasswordDto;
import com.users.dto.UserRequestDto;
import com.users.dto.UserResponseDto;
import com.users.entity.Users;
import com.users.shared.Status;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserService {
    String updateUser(UserRequestDto userRequestDto, String userId);

    UserResponseDto getUserById(String userId);

    UserResponseDto getUserByEmail(String email);

    String deleteUser(String userId);

    List<UserResponseDto> getAllUsers();

    String changeStatusToActive(String cid);

    List<UserResponseDto> findByStatus(Status status);

    List<UserResponseDto> findUserByTime(Date startDate, Date endDate);

    void sendVerification(Users customer, String siteURL) throws MessagingException, UnsupportedEncodingException;

    boolean verify(String code);

    String changePassword(String email, ChangePasswordDto request);

    Map<String,Integer> getUserByCount();
}
