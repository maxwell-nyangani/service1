package com.maxwell.oAuth2JpaMySQLjwt.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.maxwell.oAuth2JpaMySQLjwt.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.maxwell.oAuth2JpaMySQLjwt.domain.AppUser myUser = userRepository.findByUsername(username);
        if (myUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(myUser.getUsername(), myUser.getPassword(), Collections.emptyList());
    }

}
