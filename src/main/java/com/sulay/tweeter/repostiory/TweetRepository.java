package com.sulay.tweeter.repostiory;

import com.sulay.tweeter.entity.Tweet;

import com.sulay.tweeter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TweetRepository extends JpaRepository<Tweet, Long> {
    List<Tweet> findByTitleContains(String title);

    List<Tweet> findAllByAuthorId(Long authorId);

    List<Tweet> findAllByAuthor(User author);
}
