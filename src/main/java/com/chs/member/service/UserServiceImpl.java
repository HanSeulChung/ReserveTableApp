package com.chs.member.service;

import com.chs.member.Auth;
import com.chs.member.dto.MemberInput;
import com.chs.member.dto.UserDto;
import com.chs.member.entity.User;
import com.chs.member.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserId(username)
                .orElseThrow(() -> new RuntimeException("user가 없습니다."));
    }

    @Override
    public UserDto register(Auth.SignUp member) {
        boolean exits = this.userRepository.existsByUserId(member.getUserId()) ;
        if (exits) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        member.setPassword(this.passwordEncoder.encode(member.getPassword()));
        var result = this.userRepository.save(member.toUserEntity());
        return UserDto.of(result);
    }

    @Override
    public UserDto authenticate(Auth.SignIn member) {
        return null;
    }

    @Override
    public List<UserDto> list(Auth.SignIn member) {
        return null;
    }

    @Override
    public UserDto detail(String userId) {
        return null;
    }

    @Override
    public boolean updateStatus(String userId, String userStatus) {
        return false;
    }

    @Override
    public boolean updatePassword(String userId, String password) {
        return false;
    }

    @Override
    public User updateMember(Auth.SignIn member) {
        return null;
    }

    @Override
    public UserDto updateMemberPassword(MemberInput parameter) {
        return null;
    }

    @Override
    public UserDto withdraw(String userId, String password) {
        return null;
    }

    @Override
    public void updateLastLoginDt(String userId, LocalDateTime lastLoginDt) {

    }
}
