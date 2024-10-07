package com.ecommerce.login_service.controller;

import com.ecommerce.login_service.dto.AuthResponseDto;
import com.ecommerce.login_service.dto.LoginDto;
import com.ecommerce.login_service.dto.RegisterDto;
import com.ecommerce.login_service.model.Role;
import com.ecommerce.login_service.model.UserEntity;
import com.ecommerce.login_service.repository.RoleRepository;
import com.ecommerce.login_service.repository.UserRepository;
import com.ecommerce.login_service.security.CustomUserDetailService;
import com.ecommerce.login_service.security.JwtGenerator;
import com.ecommerce.login_service.service.impl.UserDetailsImpl;
import com.ecommerce.login_service.service.impl.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtGenerator jwtGenerator;

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        if(userRepository.existsByUsername(registerDto.getUsername())){
            return new ResponseEntity<>("Username already taken.",
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(registerDto.getEmail())){
            return new ResponseEntity<>("Email already taken.", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());

        Role roles = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));
        userRepository.save(user);
        return new ResponseEntity<>("User registration successful", HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){

        if(!userRepository.existsByUsername(loginDto.getUsername())) {
            return new ResponseEntity<>("Invalid Username",
                    HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
                        loginDto.getPassword()));

        //System.out.println("Principal is " + authentication.getPrincipal());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);

        //Object principal = authentication.getPrincipal();
        //System.out.println("Principal Class: " + principal.getClass());

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(
                item -> item.getAuthority()).collect(Collectors.toList());


        return new ResponseEntity<>(new AuthResponseDto(
                token,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles), HttpStatus.OK);
    }

    @PutMapping("forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody LoginDto loginDto){
        if(!userRepository.existsByUsername(loginDto.getUsername())){
            return new ResponseEntity<>("Username does not exist",
                    HttpStatus.BAD_REQUEST);
        }

        UserEntity user = userRepository.findByUsername(loginDto.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("Username does not exist")
        );
        user.setPassword(passwordEncoder.encode(loginDto.getPassword()));
        userRepository.save(user);

        return new ResponseEntity<>("Password changed", HttpStatus.OK);
    }
}
