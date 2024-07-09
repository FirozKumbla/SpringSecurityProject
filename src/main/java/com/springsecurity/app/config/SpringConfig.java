package com.springsecurity.app.config;

import com.springsecurity.app.service.MyUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Slf4j
public class SpringConfig {

    @Autowired
    MyUserDetailService myUserDetailService;
    @Autowired
    SuccessHandler successHandler;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public UserDetailsService userDetailsService(){
        return myUserDetailService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails admin = User.builder()
//                .username("firoz")
//                .password(passwordEncoder().encode("firoz@123"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.builder()
//                .username("rohan")
//                .password(passwordEncoder().encode("rohan@123"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        try {
            return httpSecurity
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(request ->
                            request.requestMatchers("/home", "/register/**").permitAll()
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .requestMatchers("/user/**").hasRole("USER")
                            .anyRequest().authenticated())
                    .formLogin(form -> form
                            .permitAll()).build();
        } catch (Exception e) {
            log.error("Error in securityFilterChain method");
        }
        return null;
    }

}
