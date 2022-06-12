package com.sulay.tweeter.mapper;

import com.sulay.tweeter.dto.UserDto;
import com.sulay.tweeter.entity.User;
import com.sulay.tweeter.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final AuthService authService;

    public UserDto mapToDto(User user) {
        User currentUser = authService.getCurrentUser();
        return UserDto.builder()
                .username(user.getUsername())
                .creationDate(user.getCreationDate())
                .profileImageUrl(user.getProfileImageUrl())
                .id(user.getId())
                .biography(user.getBiography())
                .followerCount(user.getFollowers().size())
                .email(user.getEmail())
                .isFollowing(currentUser.getFollowing().stream().map(User::getUsername)
                        .collect(Collectors.toList())
                        .contains(user.getUsername()))
                .isFollowed(currentUser.getFollowers().stream().map(User::getUsername)
                        .collect(Collectors.toList())
                        .contains(user.getUsername()))
                .build();
    }
}
