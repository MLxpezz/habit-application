package com.habit.management.service.impl;

import com.habit.management.global.enums.ApiErrorMessageEnum;
import com.habit.management.model.entity.UserEntity;
import com.habit.management.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(ApiErrorMessageEnum.USER_NOT_FOUND.getMessage()));

        List<SimpleGrantedAuthority> roles = new ArrayList<>();

        userEntity.getRoles()
                .forEach(role -> {
                    roles.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName().name())));
                });

        return User
                .builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .accountExpired(false)
                .accountLocked(false)
                .disabled(!userEntity.isEnabled())
                .authorities(roles)
                .build();
    }
}
