package com.example.appjwtrealemailauditing.service;

import com.example.appjwtrealemailauditing.dto.ApiResponse;
import com.example.appjwtrealemailauditing.dto.LoginDto;
import com.example.appjwtrealemailauditing.dto.RegisterDto;
import com.example.appjwtrealemailauditing.entity.User;
import com.example.appjwtrealemailauditing.enums.RoleName;
import com.example.appjwtrealemailauditing.repository.RoleRepository;
import com.example.appjwtrealemailauditing.repository.UserRepository;
import com.example.appjwtrealemailauditing.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JavaMailSender javaMailSender;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public ApiResponse register(RegisterDto registerDto) {
        String email = registerDto.getEmail();
        boolean existsByEmail = userRepository.existsByEmail(email);
        if (existsByEmail) return new ApiResponse("This email exist", false);

        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.ROLE_USER)));

        user.setEmailCode(UUID.randomUUID().toString());

        userRepository.save(user);
        String sendEmail = sendEmail(registerDto.getEmail(), user.getEmailCode());
        if (sendEmail == null) return new ApiResponse("Registered successfully, please verify your email", true);
        return new ApiResponse(sendEmail, true);
    }

    private String sendEmail(String email, String emailCode) {
        String text = null;
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("SpringExam@pdp.uz");
            mailMessage.setTo(email);
            mailMessage.setSubject("Account Verification");
            text = "http://192.168.43.42:8080/api/auth/verifyEmail?emailCode=" + emailCode + "&email=" + email;
            mailMessage.setText(text);
            javaMailSender.send(mailMessage);
            return null;
        } catch (Exception e) {
            return text;
        }
    }

    public ApiResponse verifyEmail(String emailCode, String email) {
        Optional<User> optionalUser = userRepository.findByEmailAndEmailCode(email, emailCode);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true);
            user.setEmailCode(null);
            userRepository.save(user);
            return new ApiResponse("Account verified!", true);
        }
        return new ApiResponse("Account already verified", false);
    }

    public ApiResponse login(LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(),
                    loginDto.getPassword()));
            User user = (User) authenticate.getPrincipal();

            String token = jwtProvider.generateToken(loginDto.getEmail(), user.getRoles());
            return new ApiResponse("Token created",true,token);

        }catch (BadCredentialsException e){
            return new ApiResponse("Password or login incorrect",false);
        }
    }
}
