//package com.frostbank.forstbank.security;
//
//import com.frostbank.forstbank.dto.UserRequest;
//import com.frostbank.forstbank.entity.User;
//import com.frostbank.forstbank.repository.UserRepository;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import java.util.Optional;
//
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class UserInfoUserDetailsService implements UserDetailsService {
//
//    private UserRepository repository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> userInfo = repository.findByEmailId(username);
//
//        return userInfo.map(UserInfoUserDetails::new)
//                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
//
//    }
//}