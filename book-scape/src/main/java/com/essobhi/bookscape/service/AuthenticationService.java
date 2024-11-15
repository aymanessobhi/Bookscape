package com.essobhi.bookscape.service;

import com.essobhi.bookscape.domain.User;
import com.essobhi.bookscape.dto.AuthRequest;
import com.essobhi.bookscape.dto.AuthResponse;
import com.essobhi.bookscape.dto.RegistrationRequest;
import jakarta.mail.MessagingException;

public interface AuthenticationService {
    void register (RegistrationRequest request) throws MessagingException;
    AuthResponse authenticate(AuthRequest request);
    //void activateAccount(String token);
    void sendValidationEmail(User user) throws MessagingException;

}
