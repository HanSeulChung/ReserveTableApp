package com.chs.admin.service;

import com.chs.admin.dto.OwnerDto;
import com.chs.admin.repository.OwnerRepository;
import com.chs.member.dto.MemberInput;
import com.chs.member.model.Auth;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class OwnerServiceImpl implements OwnerService{
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return ownerRepository.findByUserId(username)
                .orElseThrow(() -> new RuntimeException("user가 없습니다."));
    }
    @Override
    public OwnerDto register(Auth.SignUp member) {
        boolean exits = this.ownerRepository.existsByUserId(member.getUserId()) ;
        if (exits) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        member.setPassword(this.passwordEncoder.encode(member.getPassword()));
        var result = this.ownerRepository.save(member.toOwnerEntity());
        return OwnerDto.of(result);
    }

    @Override
    public OwnerDto authenticate(Auth.SignIn member) {
        var user = this.ownerRepository.findByUserId(member.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 아이디가 존재하지 않습니다."));

        if (!this.passwordEncoder.matches(member.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return OwnerDto.of(user);
    }

    @Override
    public List<OwnerDto> list(Auth.SignIn member) {
        return null;
    }

    @Override
    public OwnerDto detail(String userId) {
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
    public OwnerDto updateMember(Auth.SignIn member) {
        return null;
    }

    @Override
    public OwnerDto updateMemberPassword(MemberInput parameter) {
        return null;
    }

    @Override
    public OwnerDto withdraw(String userId, String password) {
        return null;
    }

    @Override
    public void updateLastLoginDt(String userId, LocalDateTime lastLoginDt) {

    }

}
