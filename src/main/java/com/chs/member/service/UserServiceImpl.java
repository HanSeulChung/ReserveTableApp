package com.chs.member.service;

import com.chs.member.Auth;
import com.chs.member.dto.MemberInput;
import com.chs.member.dto.UserDto;
import com.chs.member.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public UserDto register(Auth.SignUp member) {
        return null;
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
