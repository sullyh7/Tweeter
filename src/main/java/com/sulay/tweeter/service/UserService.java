package com.sulay.tweeter.service;

import com.sulay.tweeter.dto.TweetResponse;
import com.sulay.tweeter.dto.UserDto;
import com.sulay.tweeter.entity.User;
import com.sulay.tweeter.exception.TweeterException;
import com.sulay.tweeter.exception.UserNotFoundException;
import com.sulay.tweeter.mapper.TweetMapper;
import com.sulay.tweeter.mapper.UserMapper;
import com.sulay.tweeter.repostiory.TweetRepository;
import com.sulay.tweeter.repostiory.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public List<UserDto> getUsers() {
       return userRepository.findAll().stream()
               .map(userMapper::mapToDto)
               .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("No user with id " + id)
        );
        return userMapper.mapToDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getFollowers(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new UserNotFoundException("No user with id " + userId));
        return user.getFollowers().stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserDto> getFollowing(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new UserNotFoundException("No user with id " + userId));
        return user.getFollowing().stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getCurrentUserFollowers() {
        User currentUser = authService.getCurrentUser();
        return currentUser.getFollowers().stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public UserDto getCurrentUser() {
        return userMapper.mapToDto(authService.getCurrentUser());
    }


}
