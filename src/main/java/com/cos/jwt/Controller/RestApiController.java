package com.cos.jwt.Controller;

import com.cos.jwt.Model.User;
import com.cos.jwt.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RestApiController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("home")
    public String home() {
        return "<h1>Hello World</h1>";
    }

//    @PostMapping("token")
//    public String token() {
//        return "<h1>Token</h1>";
//    }

    @PostMapping("/join")
    public String join(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles("USER"); // 권한은 기본으로 USER로 설정합니다. ---> security 최신 버전에서는 권한 적용시 ROLE_ 쓰지 않음.
        userRepository.save(user);
        return "회원가입완료";
    }
}
