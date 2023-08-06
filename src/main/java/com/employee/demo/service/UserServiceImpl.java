package com.employee.demo.service;


import com.employee.demo.entity.PasswordResetToken;
import com.employee.demo.entity.User;

import java.util.Optional;
import java.util.UUID;
import com.employee.demo.entity.VerificationToken;
import com.employee.demo.event.RegistrationCompleteEvent;
import com.employee.demo.model.UserModel;
import com.employee.demo.repository.PasswordResetTokenRepository;
import com.employee.demo.repository.UserRepository;
import com.employee.demo.repository.VertificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationEventPublisher;
import java.util.Calendar;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Or other implementations of PasswordEncoder



@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;


    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VertificationTokenRepository vertificationTokenRepository;

    @Override
    public User registerUser(UserModel userModel){
        User user = new User();
        user.setEmail(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
        user.setFirstName(userModel.getLastName());
        user.setRole("USER");
        user.setPassword(encoder.encode(userModel.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public void saveVerficationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user,token);
        vertificationTokenRepository.save(verificationToken);

    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = vertificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            return "invalid";
        }
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0) {
            vertificationTokenRepository.delete(verificationToken);
            return "expired";
        }

        user.setEnabled(true);
        userRepository.save(user);
        return "valid";



    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = vertificationTokenRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        vertificationTokenRepository.save(verificationToken);
        return verificationToken;

    }

    @Override
    public User findUserbyEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user,token);
        passwordResetTokenRepository.save(passwordResetToken);


    }

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken= passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null) {
            return "invalid";
        }
        User user = passwordResetToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((passwordResetToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return "expired";
        }

        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

    }

}
