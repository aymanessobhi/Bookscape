package com.essobhi.bookscape.service;


import com.essobhi.bookscape.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    @Transactional //when i load the user, i want to load the roles of the authorities with it
    public UserDetails loadUserByUsername(String userEamil) throws UsernameNotFoundException {
        return userRepository.findByEmail(userEamil)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
