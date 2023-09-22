package com.chs.member.service;

import com.chs.admin.entity.Owner;
import com.chs.admin.repository.OwnerRepository;
import com.chs.member.model.Auth;
import com.chs.member.dto.MemberInput;
import com.chs.member.dto.UserDto;
import com.chs.member.entity.User;
import com.chs.member.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUserId(username);
        Optional<Owner> owner = ownerRepository.findByUserId(username);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

       if (owner.isPresent()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_OWNER"));
            return new org.springframework.security.core.userdetails.User(
                    owner.get().getUserId(), owner.get().getPassword(), grantedAuthorities);
        } else if (user.isPresent()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new org.springframework.security.core.userdetails.User(
                    user.get().getUserId(), user.get().getPassword(), grantedAuthorities);
        }
        throw new UsernameNotFoundException("user가 없습니다.");
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

        var user = this.userRepository.findByUserId(member.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 아이디가 존재하지 않습니다."));

        if (!this.passwordEncoder.matches(member.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return UserDto.of(user);
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
