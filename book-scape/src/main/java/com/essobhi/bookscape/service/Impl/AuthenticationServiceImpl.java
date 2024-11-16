package com.essobhi.bookscape.service.Impl;

import com.essobhi.bookscape.domain.Token;
import com.essobhi.bookscape.domain.User;
import com.essobhi.bookscape.dto.AuthRequest;
import com.essobhi.bookscape.dto.AuthResponse;
import com.essobhi.bookscape.dto.RegistrationRequest;
import com.essobhi.bookscape.enums.EmailTemplateName;
import com.essobhi.bookscape.repository.RoleRepository;
import com.essobhi.bookscape.repository.TokenRepository;
import com.essobhi.bookscape.repository.UserRepository;
import com.essobhi.bookscape.security.JwtService;
import com.essobhi.bookscape.service.AuthenticationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final EmailServiceImpl emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;
    @Override
    public void register(RegistrationRequest request) throws MessagingException {
        //assign a role by default
        var userRole = roleRepository.findByName("USER")
                // todo - better exception handling
                .orElseThrow(() -> new IllegalStateException("ROLE USER was not initialized"));
        //create a user object and save it
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);

        //send validation email
        sendValidationEmail(user);
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName",user.fullName());
        var jwtToken = jwtService.generateToken(claims,user);
        return AuthResponse.builder()
                .fullName(user.fullName())
                .token(jwtToken)
                .email(user.getEmail())
                .build();
    }

    @Override
    public void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    @Override
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(()-> new RuntimeException("Invalid token"));
        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())){
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation has expired. A new Token has been sent to the same email address");
        }
        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(()-> new UsernameNotFoundException("User not Found"));
        user.setEnabled(true);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    private String generateAndSaveActivationToken(User user) {
        // Generate a token
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);

        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for(int i = 0; i < length; i++){
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
}
