package com.chs.member.user.service;

import com.chs.exception.Impl.AlreadyExistUserException;
import com.chs.exception.Impl.NoUserIdException;
import com.chs.exception.Impl.UnmatchPasswordException;
import com.chs.member.owner.entity.Owner;
import com.chs.member.owner.repository.OwnerRepository;
import com.chs.member.user.dto.UserDto;
import com.chs.member.user.entity.User;
import com.chs.member.user.repository.UserRepository;
import com.chs.member.model.Auth;
import com.chs.member.user.dto.MemberInput;
import com.chs.type.MemberStatus;
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
            throw new AlreadyExistUserException();
        }

        member.setPassword(this.passwordEncoder.encode(member.getPassword()));
        var result = this.userRepository.save(member.toUserEntity());
        return UserDto.of(result);
    }

    @Override
    public UserDto authenticate(Auth.SignIn member) {

        var user = this.userRepository.findByUserId(member.getUserId())
                .orElseThrow(() -> new NoUserIdException());

        if (!this.passwordEncoder.matches(member.getPassword(), user.getPassword())) {
            throw new UnmatchPasswordException();
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
    public boolean updateStatus(String userId, MemberStatus userStatus) {

        Optional<User> user = userRepository.findByUserId(userId);
        if (!user.isPresent()) {
            throw new NoUserIdException();
        }
        if (user.get().getStatus().equals(MemberStatus.WITHDRAW)) {
            throw new RuntimeException("이미 탈퇴한 회원입니다.");
        }

        UserDto userDto = UserDto.of(user.get());
        userDto.setStatus(userStatus);
        userRepository.save(User.toEntity(userDto));

        return true;
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
