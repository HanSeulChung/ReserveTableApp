package com.chs.member.owner.entity;

import com.chs.member.owner.dto.OwnerDto;
import com.chs.member.user.dto.UserDto;
import com.chs.member.user.entity.User;
import com.chs.type.MemberStatus;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "OWNER")
public class Owner implements UserDetails {
    @Id
    private String userId;
    private String userName;
    private String phone;
    private String email;
    private String password;
    private LocalDateTime regDt;
    private LocalDateTime udtDt;

    private boolean adminYn;

    private MemberStatus status;  //이용가능한상태, 정지상태
    private LocalDateTime lastLoginDt;

    private String resetPasswordKey;
    private LocalDateTime resetPasswordLimitDt;

    @OneToMany(mappedBy = "owner")
    private List<Store> stores;

    public static Owner toEntity(OwnerDto ownerDto) {
        return Owner.builder()
                .userId(ownerDto.getUserId())
                .userName(ownerDto.getUserName())
                .email(ownerDto.getEmail())
                .phone(ownerDto.getPhone())
                .password(ownerDto.getPassword())
                .regDt(ownerDto.getRegDt())
                .udtDt(ownerDto.getUdtDt())
                .adminYn(ownerDto.isAdminYn())
                .status(ownerDto.getStatus())
                .lastLoginDt(ownerDto.getLastLoginDt())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_OWNER"));
        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
