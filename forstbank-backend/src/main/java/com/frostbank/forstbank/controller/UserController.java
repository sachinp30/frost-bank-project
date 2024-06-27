//package com.frostbank.forstbank.controller;
//
//import com.frostbank.forstbank.dto.AuthRequest;
//import com.frostbank.forstbank.dto.UserRequest;
//import com.frostbank.forstbank.entity.User;
//import com.frostbank.forstbank.service.impl.UserServiceImpl;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/v1/user")
//public class UserController {
//
//    //private UserService userService;
//    private UserServiceImpl userService;
//
//    private AuthenticationManager authenticationManager;
//
//    private JwtService jwtService;
//
//    @PostMapping("/addNew")
//    public ResponseEntity<String> addNewUser(@RequestBody UserRequest userEntryDto) {
//        try {
//            User user = userService.addUser(userEntryDto);
//            return new ResponseEntity<>("User Saved Successfully", HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PostMapping("/getToken")
//    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//
//        if (authentication.isAuthenticated()) {
//            return jwtService.generateToken(authRequest.getUsername());
//        }
//
//        throw new UsernameNotFoundException("invalid user details.");
//    }
//}
