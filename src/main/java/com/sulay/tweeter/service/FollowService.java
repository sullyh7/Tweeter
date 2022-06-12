package com.sulay.tweeter.service;

import com.sulay.tweeter.entity.User;
import com.sulay.tweeter.exception.TweeterException;
import com.sulay.tweeter.exception.UserNotFoundException;
import com.sulay.tweeter.repostiory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final UserRepository userRepository;
    private final AuthService authService;

    @Transactional
    public void followUser(Long userId) {
        User userToFollow = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("No user with id " + userId)
        );

        if (authService.getCurrentUser().getFollowing().stream().map(User::getUsername)
                .collect(Collectors.toList())
                .contains(userToFollow.getUsername())) {
            throw new TweeterException("Already following user with id " + userToFollow.getId());
        }

        if (authService.getCurrentUsername().equals(userToFollow.getUsername())) {
            throw new TweeterException("You can't follow yourself");
        }

        userToFollow.addFollower(authService.getCurrentUser());
        User current = userRepository.findByUsername(authService.getCurrentUsername()).orElseThrow(
                () -> new TweeterException("Unexpected error")
        );
        current.addFollowing(userToFollow);

    }

    @Transactional
    public void unfollowUser(Long userId) {
        User userToUnfollow = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("No used with id " + userId)
        );
        if (authService.getCurrentUser().getFollowing().stream().map(User::getUsername)
                .collect(Collectors.toList())
                .contains(userToUnfollow.getUsername())) {
            authService.getCurrentUser().removeFollowing(userToUnfollow);
            userToUnfollow.removeFollower(authService.getCurrentUser());
            userRepository.save(authService.getCurrentUser());
        }

    }
}
