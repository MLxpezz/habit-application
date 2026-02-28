package com.habit.management.service.impl;

import com.habit.management.model.dto.UserDto;
import com.habit.management.model.entity.UserEntity;
import com.habit.management.repository.UserRepository;
import com.habit.management.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(UserDto userDto) {
        if (userDto != null) {
            userRepository.save(UserEntity
                    .builder()
                            .email(userDto.email())
                            .password(userDto.password())
                    .build());
        }
    }
}
