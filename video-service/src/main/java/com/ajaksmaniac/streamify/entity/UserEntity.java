package com.ajaksmaniac.streamify.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@RequiredArgsConstructor
@Table(name = "user")
@Entity
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "isActive", nullable = false )
    private boolean isActive;

    @OneToOne()
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    @ManyToMany
    @JoinTable(
            name = "user_subscribed_channels",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id")
    )
    private List<ChannelEntity> subscribedChannels;


    public String getIdString() {
        return id.toString();
    }

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.EMPTY_LIST;
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

    public void subscribe(ChannelEntity channel){
        this.subscribedChannels.add(channel);

    }
    public void unsubscribe(ChannelEntity channel){
        this.subscribedChannels.remove(channel);

    }

}
