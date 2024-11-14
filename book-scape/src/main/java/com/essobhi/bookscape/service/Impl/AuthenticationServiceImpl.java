package com.essobhi.bookscape.service.Impl;

import com.essobhi.bookscape.dto.AuthRequest;
import com.essobhi.bookscape.dto.AuthResponse;
import com.essobhi.bookscape.dto.RegistrationRequest;
import com.essobhi.bookscape.service.AuthenticationService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public void register(RegistrationRequest request) {

    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        return null;
    }
}
