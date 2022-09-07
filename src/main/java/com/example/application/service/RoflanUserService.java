package com.example.application.service;

import com.example.application.repository.RoflanUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoflanUserService implements UserDetailsService {
    private final RoflanUserRepository roflanUserRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return roflanUserRepository.findByUsername(username)
                .orElse(null);
    }
}
