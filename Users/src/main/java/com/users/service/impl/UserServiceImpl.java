package com.users.service.impl;

import com.users.dto.ChangePasswordDto;
import com.users.dto.UserRequestDto;
import com.users.dto.UserResponseDto;
import com.users.entity.Users;
import com.users.exception.EmailNotFoundException;
import com.users.exception.ResourcenotFoundException;
import com.users.repo.UserRepo;
import com.users.service.UserService;
import com.users.shared.MessageConstant;
import com.users.shared.Status;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    @Override
    public String updateUser(UserRequestDto userRequestDto, String userId) {
        Users users = userRepo.findById(userId).orElseThrow(() -> new ResourcenotFoundException("User", "UserId", userId));
        users.setFirstname(userRequestDto.getFirstname());
        users.setLastname(userRequestDto.getLastname());
        users.setContactNumber(userRequestDto.getContactNumber());
        users.setAddress(userRequestDto.getAddress());
        users.setFullname(userRequestDto.getFirstname() + " " + userRequestDto.getLastname());
        userRepo.save(users);
        return "User details have been updated successfully";
    }

    @Override
    public UserResponseDto getUserById(String userId) {
        Users users = userRepo.findById(userId).orElseThrow(() -> new ResourcenotFoundException("User", "UserId", userId));
        return modelMapper.map(users, UserResponseDto.class);
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        Users users = userRepo.findByEmail(email).orElseThrow(() -> new EmailNotFoundException("User", "UserId", email));
        return modelMapper.map(users, UserResponseDto.class);
    }

    @Override
    public String deleteUser(String userId) {
        Users users = userRepo.findById(userId).orElseThrow(() -> new ResourcenotFoundException("User", "UserId", userId));
        users.setStatus(Status.DELETE);
        userRepo.save(users);
        return "User has been deleted successfully";
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<Users> listOfCustomer = userRepo.findAll();
        return listOfCustomer.stream().map(list -> modelMapper.map(list, UserResponseDto.class)).toList();

    }

    @Override
    public String changeStatusToActive(String cid) {
        Users users = userRepo.findById(cid).orElseThrow(() -> new ResourcenotFoundException("User", "UserId", cid));
        users.setStatus(Status.ACTIVE);
        userRepo.save(users);
        return "User status has been changed successfully";
    }

    @Override
    public List<UserResponseDto> findByStatus(Status status) {
        List<Users> listOfCustomer = userRepo.findByStatus(status);
        return listOfCustomer.stream().map(list -> modelMapper.map(list, UserResponseDto.class)).toList();

    }

    @Override
    public List<UserResponseDto> findUserByTime(Date startDate, Date endDate) {
        List<Users> listOfCustomer = userRepo.findByCreatedDateBetween(startDate, endDate);
        return listOfCustomer.stream().map(list -> modelMapper.map(list, UserResponseDto.class)).toList();

    }

    @Override
    public void sendVerification(Users customer, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = customer.getEmail();
        String fromAddress = MessageConstant.fromAddress;
        String senderName = MessageConstant.senderName;
        String subject = MessageConstant.subject;
        String content = "Dear " + customer.getUsername() + "<br>"
                + "Please click the link below to verify your account:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Central Library Management";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", customer.getUsername());
        String verifyURL = siteURL + "/api/user/auth/verify?code=" + customer.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @Override
    public boolean verify(String code) {
        Users customer = this.userRepo.findByVerificationCode(code);
        if (customer == null) {
            return false;
        } else {
            customer.setVerificationCode(null);
            if (customer.getStatus() == Status.UNVERIFIED || customer.getStatus()==Status.INACTIVE) {
                customer.setStatus(Status.ACTIVE);
            }
            this.userRepo.save(customer);
            return true;
        }
    }

    @Override
    public String changePassword(String email, ChangePasswordDto request) {
        Users users=userRepo.findByEmail(email).orElseThrow(()->new EmailNotFoundException("User","email",email));
        if(users.getStatus()==Status.ACTIVE && passwordEncoder.matches(request.getOldPassword(), users.getPassword())) {
            users.setPassword(passwordEncoder.encode(request.getNewPassword()));
            users.setLastPasswordChanged(new Date());
            userRepo.save(users);
            return "Password has been changed successfully";
        }
        return "Invalid old password";
    }

    @Override
    public Map<String, Integer> getUserByCount() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Total",(int)userRepo.count());
        map.put("Unverified",userRepo.countByStatus(Status.UNVERIFIED));
        map.put("Inactive",userRepo.countByStatus(Status.INACTIVE));
        map.put("Active",userRepo.countByStatus(Status.ACTIVE));
        map.put("Delete",userRepo.countByStatus(Status.DELETE));
        return map;
    }
}
