package com.example.codefellowship.security;

import com.example.codefellowship.domain.ApplicationUser;
import com.example.codefellowship.infrastructure.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ApplicationUserRepository applicationUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = this.applicationUserRepo.findByUsername(username);

        if (applicationUser == null) {
            System.out.println("User not found: " + username);
            throw new UsernameNotFoundException("User " + username + " was not found.");
        }

        System.out.println("Found User: " + applicationUser);
        return applicationUser;
    }
}
