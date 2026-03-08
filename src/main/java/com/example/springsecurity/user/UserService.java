package com.example.springsecurity.user;

import com.example.springsecurity.user.model.AuthUser;
import com.example.springsecurity.user.model.User;
import com.example.springsecurity.user.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService  {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow();
        return AuthUser.fromEntity(user);
    }

    public void signup(UserDto.SignupRequest dto) {
        // 스프링 시큐리티는 기본적으로 유저 정보를 조회할 때 '암호화'된 것으로 간주한다.
        // 따라서, 회원 가입 시 암호화된 패스워드를 저장해야한다.
        User entity = dto.toEntity(passwordEncoder);
        userRepository.save(entity);
    }
}
