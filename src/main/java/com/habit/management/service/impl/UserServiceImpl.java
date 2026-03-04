package com.habit.management.service.impl;

import com.habit.management.enums.ApiErrorMessageEnum;
import com.habit.management.enums.ApiSuccessfullyMessageEnum;
import com.habit.management.enums.RoleEnum;
import com.habit.management.model.dto.UserDto;
import com.habit.management.model.entity.RoleEntity;
import com.habit.management.model.entity.UserEntity;
import com.habit.management.repository.RoleRepository;
import com.habit.management.repository.UserRepository;
import com.habit.management.service.IUserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public String createUser(UserDto userDto) {

        if(userRepository.findByEmail(userDto.email()).isPresent()) {
            throw new EntityExistsException(ApiErrorMessageEnum.EMAIL_ALREADY_EXISTS.getMessage());
        }

        RoleEntity userRole = roleRepository.findByName(RoleEnum.COMMON_USER)
                .orElseThrow(() -> new EntityNotFoundException(ApiErrorMessageEnum.ROLE_NOT_FOUND.getMessage()));

        userRepository.save(
          UserEntity
                  .builder()
                  .email(userDto.email())
                  .password(passwordEncoder.encode(userDto.password()))
                  .roles(Set.of(userRole))
                  .build()
        );

        return ApiSuccessfullyMessageEnum.SUCCESS_REGISTER.getMessage();
    }
}
