package com.users.controller;

import com.users.dto.ChangePasswordDto;
import com.users.dto.DateRequestDto;
import com.users.dto.UserRequestDto;
import com.users.dto.UserResponseDto;
import com.users.service.UserService;
import com.users.shared.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/update/{userId}")
    public String updateUser(@RequestBody UserRequestDto user, @PathVariable String userId) {
        return userService.updateUser(user, userId);
    }

    @GetMapping("/delete/{userId}")
    public String deleteUserStatus(@PathVariable String userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping("/getAll")
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/verifyUser/{userId}")
    public String verifyUser(@PathVariable String userId) {
        return userService.changeStatusToActive(userId);
    }

    @GetMapping("/getById/{userId}")
    public UserResponseDto getUserById(@PathVariable String userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/getByStatus")
    public List<UserResponseDto> getUsersByStatus(@PathVariable String status) {
        return userService.findByStatus(Status.valueOf(status));
    }

    @PostMapping("/filterByDate")
    public List<UserResponseDto> filterUsersByDate(@RequestBody DateRequestDto dateDto) {
        return userService.findUserByTime(dateDto.getStartDate(), dateDto.getEndDate());
    }

    @PostMapping("/changePassword/{email}")
    public String changePassword(@PathVariable String email, @RequestBody ChangePasswordDto dto) {
        return userService.changePassword(email, dto);
    }

    @GetMapping("/test")
    public String testMe() {
        return "I am user";
    }
}
