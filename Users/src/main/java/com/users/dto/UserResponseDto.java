package com.users.dto;

import lombok.Data;

@Data
public class UserResponseDto {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String fullname;
    private String role;
    private String status;
    private String contactNumber;
    private String address;
}
