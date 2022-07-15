package com.bksoftwarevn.auction.model;

import com.bksoftwarevn.auction.dto.RoleDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class UserDetailCustomize extends User {
    private String id;
    private String email;
    private String name;
    private String phone;
    private String address;
    private String avatar;
    private String lang;
    private String createDate;
    private boolean lock;
    private boolean active;
    private String citizenId;
    private Set<RoleDto> roles;


    public UserDetailCustomize(String username, String password, Collection<? extends GrantedAuthority> authorities,
                               String id, String email) {
        super(username, password, authorities);
        this.email = email;
        this.id = id;
    }


    public UserDetailCustomize(String username, String password, Collection<? extends GrantedAuthority> authorities, String id,
                               String email, String name, String address, String phone, boolean active, boolean lock,
                               String citizenId, Set<RoleDto> roles, String avatar, String lang, Instant createdDate) {
        super(username, password, active, true, true, !lock, authorities);
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.citizenId = citizenId;
        this.roles = roles;
        this.avatar = avatar;
        this.address = address;
        this.lang = lang;
        this.createDate = createdDate.toString();
    }
}
