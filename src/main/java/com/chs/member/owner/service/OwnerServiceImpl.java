package com.chs.member.owner.service;

import com.chs.exception.Impl.*;
import com.chs.member.model.Auth;
import com.chs.member.owner.dto.OwnerDto;
import com.chs.member.owner.entity.Owner;
import com.chs.member.owner.repository.OwnerRepository;
import com.chs.member.user.dto.MemberInput;
import com.chs.member.user.entity.User;
import com.chs.member.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class OwnerServiceImpl implements OwnerService{
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean register(Auth.SignUp member) {
        boolean returnValue = false;
        boolean exits = this.ownerRepository.existsByUserId(member.getUserId());
        if (exits) {
            throw new AlreadyExistUserException();
        }
        Optional<User> user = userRepository.findByEmail(member.getEmail());
        Optional<Owner> owner = ownerRepository.findByEmail(member.getEmail());
        if (owner.isPresent() || user.isPresent()) {
            throw new AlreadyExistEmailException();
        }

        member.setPassword(this.passwordEncoder.encode(member.getPassword()));
        this.ownerRepository.save(member.toOwnerEntity());
        returnValue = true;

        return returnValue;
    }

    @Override
    public OwnerDto authenticate(Auth.SignIn member) {
        var user = this.ownerRepository.findByUserId(member.getUsername())
                .orElseThrow(() -> new NoUserIdException());

        if (!this.passwordEncoder.matches(member.getPassword(), user.getPassword())) {
            throw new UnmatchPasswordException();
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
        Optional<Owner> owner = ownerRepository.findByUserId(userId);

        if(!owner.isPresent()) {
            throw new NoUserIdException();
        }

        Owner presentOwner = owner.get();
        OwnerDto ofDto = OwnerDto.of(presentOwner);
        ofDto.setLastLoginDt(lastLoginDt);

        ownerRepository.save(Owner.toEntity(ofDto));
    }

}
