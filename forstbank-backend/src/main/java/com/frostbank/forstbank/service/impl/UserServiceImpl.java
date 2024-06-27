//package com.frostbank.forstbank.service.impl;
//
//import com.frostbank.forstbank.dto.UserRequest;
//import com.frostbank.forstbank.entity.User;
//import com.frostbank.forstbank.exception.UserExistException;
//import com.frostbank.forstbank.mapper.UserMapper;
//import com.frostbank.forstbank.repository.UserRepository;
//import com.frostbank.forstbank.service.UserServiceInt;
//import lombok.AllArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@AllArgsConstructor
//public class UserServiceImpl implements UserServiceInt {
//    private UserRepository userRepository;
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public User addUser(UserRequest userRequest) {
//        Optional<User> users = userRepository.findByEmailId(userRequest.getEmailId());
//
//        if (users.isPresent()) {
//            throw new UserExistException("User already exists");
//        }
//
//        User user = UserMapper.userDtoToUser(userRequest,  passwordEncoder.encode("1234"));
//
//        userRepository.save(user);
//        return user;
//    }
//}
