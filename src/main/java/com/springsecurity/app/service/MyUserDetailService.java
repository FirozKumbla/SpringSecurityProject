package com.springsecurity.app.service;

import com.springsecurity.app.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import com.springsecurity.app.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> user = userRepository.findByUserName(username);
        if (user.isPresent()) {
            UserModel userObj = user.get();
            return User.builder().username(userObj.getUserName())
                    .password(userObj.getPassword())
                    .roles(userObj.getRole())
                    .build();
        }else {
            log.error("User not found");
            throw new UsernameNotFoundException(username);
        }
    }
}
