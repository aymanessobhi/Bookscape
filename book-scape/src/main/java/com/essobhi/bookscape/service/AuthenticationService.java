package com.essobhi.bookscape.service;

import com.essobhi.bookscape.dto.AuthRequest;
import com.essobhi.bookscape.dto.AuthResponse;
import com.essobhi.bookscape.dto.RegistrationRequest;

public interface AuthenticationService {
    void register (RegistrationRequest request);
    AuthResponse authenticate(AuthRequest request);
    //void activateAccount(String token);
    //

}
