package com.chs.member.user.entity;

import com.chs.member.user.dto.UserDto;
import com.chs.reservation.entity.Reservation;
import com.chs.review.entity.Review;
import com.chs.type.MemberStatus;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "USER")
@Builder
public class User implements UserDetails {
    @Id
    private String userId;
    private String password;
    private String userName;
    private String email;
    private String phone;
    private LocalDateTime regDt;
    private LocalDateTime udtDt;

    private boolean adminYn;

    private MemberStatus status;  //이용가능한상태, 정지상태
    private LocalDateTime lastLoginDt;

    private int penalty;

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    public static User toEntity(UserDto userDto) {
        return User.builder()
                .userId(userDto.getUserId())
                .userName(userDto.getUserName())
                .phone(userDto.getPhone())
                .password(userDto.getPassword())
                .penalty(userDto.getPenalty())
                .regDt(userDto.getRegDt())
                .udtDt(userDto.getUdtDt())
                .adminYn(userDto.isAdminYn())
                .status(userDto.getStatus())
                .lastLoginDt(userDto.getLastLoginDt())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
