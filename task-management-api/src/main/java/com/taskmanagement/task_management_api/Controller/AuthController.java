package com.taskmanagement.task_management_api.Controller;

import com.taskmanagement.task_management_api.DTO.AuthRequest;
import com.taskmanagement.task_management_api.DTO.AuthResponse;
import com.taskmanagement.task_management_api.Exception.AuthenticationFailed;
import com.taskmanagement.task_management_api.Service.UserService;
import com.taskmanagement.task_management_api.Util.JwtUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/signin")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<AuthResponse> signin (@RequestBody AuthRequest authRequest){
        AuthResponse authResponse=userService.addUser(authRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
    @PostMapping("/token")
    public String generateToken(@RequestBody AuthRequest authRequest){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(), authRequest.getPassword())
            );
            return jwtUtil.generateToken(authRequest.getUsername());
        } catch (Exception e) {
            throw new AuthenticationFailed(e);

        }
    }
}
