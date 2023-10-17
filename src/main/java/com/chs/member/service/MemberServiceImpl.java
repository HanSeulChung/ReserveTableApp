package com.chs.member.service;

import com.chs.components.MailComponents;
import com.chs.exception.Impl.InvalidResetPasswordDt;
import com.chs.exception.Impl.NoUserIdException;
import com.chs.exception.Impl.UnmatchPasswordException;
import com.chs.exception.Impl.UserNotFoundException;
import com.chs.member.model.ResetPasswordInput;
import com.chs.member.owner.entity.Owner;
import com.chs.member.owner.repository.OwnerRepository;
import com.chs.member.owner.service.OwnerService;
import com.chs.member.user.entity.User;
import com.chs.member.user.repository.UserRepository;
import com.chs.member.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final MailComponents mailComponents;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUserId(username);
        Optional<Owner> owner = ownerRepository.findByUserId(username);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        UserDetails userDetails = null;
        if (owner.isPresent()) {
            Owner ownerPresent = owner.get();
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_OWNER"));
            // 로그인 성공 시 Authentication 객체 생성
            userDetails = new org.springframework.security.core.userdetails.User(
                    ownerPresent.getUserId(), ownerPresent.getPassword(), grantedAuthorities);

            // 로그인 성공 시 Authentication 객체 생성
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", grantedAuthorities);

            // SecurityContextHolder에 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if (user.isPresent()) {
            User userPresent = user.get();
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            userDetails = new org.springframework.security.core.userdetails.User(
                    userPresent.getUserId(), userPresent.getPassword(), grantedAuthorities);

            // 로그인 성공 시 Authentication 객체 생성
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", grantedAuthorities);

            // SecurityContextHolder에 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        if (userDetails == null) {
            throw new UserNotFoundException(username);
        }

        return userDetails;
    }


    @Override
    public boolean authenticate(String username, String password) {

        Optional<User> user = userRepository.findByUserId(username);
        Optional<Owner> owner = ownerRepository.findByUserId(username);
        if (owner.isPresent()) {
            if (!this.passwordEncoder.matches(password, owner.get().getPassword())) {
                throw new UnmatchPasswordException();
            }

        } else if(user.isPresent()) {
            if (!this.passwordEncoder.matches(password, user.get().getPassword())) {
                throw new UnmatchPasswordException();
            }

        } else {
            throw new NoUserIdException();
        }

        return true;
    }
    @Override
    public boolean sendResetPassword(ResetPasswordInput parameter) {

        Optional<User> optionalUser = userRepository.findByUserIdAndUserName(parameter.getUserId(), parameter.getUserName());
        Optional<Owner> optionalOwner = ownerRepository.findByUserIdAndUserName(parameter.getUserId(), parameter.getUserName());
        if (!optionalUser.isPresent() && !optionalOwner.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        String uuid = UUID.randomUUID().toString();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setResetPasswordKey(uuid);
            user.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));
            userRepository.save(user);
        } else {
            Owner owner = optionalOwner.get();
            owner.setResetPasswordKey(uuid);
            owner.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));
            ownerRepository.save(owner);
        }

        String email = parameter.getUserEmail();
        String subject = "[ReserveTableApp] 비밀번호 초기화 메일 입니다. ";
        String text = "<p>ReserveTableApp 비밀번호 초기화 메일 입니다.<p>" +
                "<p>아래 링크를 클릭하셔서 비밀번호를 초기화 해주세요.</p>"+
                "<div><a target='_blank' href='http://localhost:8080/reset/password?id=" + uuid + "'> 비밀번호 초기화 링크 </a></div>";
        mailComponents.sendMail(email, subject, text);

        return false;
    }

    @Override
    public boolean resetPassword(String uuid, String password) {

        String encPassword = "";
        Optional<User> optionalUser = userRepository.findByResetPasswordKey(uuid);
        Optional<Owner> optionalOwner = ownerRepository.findByResetPasswordKey(uuid);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (user.getResetPasswordLimitDt() == null) {
                throw new InvalidResetPasswordDt();
            }

            if (user.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
                throw new InvalidResetPasswordDt();
            }

            encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            user.setPassword(encPassword);
            user.setResetPasswordKey("");
            user.setResetPasswordLimitDt(null);
            userRepository.save(user);
        } else {
            Owner owner = optionalOwner.get();
            if (owner.getResetPasswordLimitDt() == null) {
                throw new InvalidResetPasswordDt();
            }

            if (owner.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
                throw new InvalidResetPasswordDt();
            }

            encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            owner.setPassword(encPassword);
            owner.setPassword(encPassword);
            owner.setResetPasswordKey("");
            owner.setResetPasswordLimitDt(null);
            ownerRepository.save(owner);
        }

        return true;
    }

    @Override
    public boolean checkResetPassword(String uuid) {
        Optional<User> optionalUser = userRepository.findByResetPasswordKey(uuid);
        Optional<Owner> optionalOwner = ownerRepository.findByResetPasswordKey(uuid);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (user.getResetPasswordLimitDt() == null) {
                throw new InvalidResetPasswordDt();
            }

            if (user.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
                throw new InvalidResetPasswordDt();
            }

        } else {
            Owner owner = optionalOwner.get();
            if (owner.getResetPasswordLimitDt() == null) {
                throw new InvalidResetPasswordDt();
            }

            if (owner.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
                throw new InvalidResetPasswordDt();
            }
        }

        return true;
    }
}
