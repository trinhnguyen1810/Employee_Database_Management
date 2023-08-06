package com.employee.demo.service;

import com.employee.demo.entity.User;
import com.employee.demo.entity.VerificationToken;
import com.employee.demo.model.UserModel;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public interface UserService {
    User registerUser(UserModel userModel);

    void saveVerficationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

    User findUserbyEmail(String email);

    void createPasswordResetTokenForUser(User user, String token);

    String validatePasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    void changePassword(User user, String newPassword);
}
