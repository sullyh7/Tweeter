package com.sulay.tweeter.repostiory;

import com.sulay.tweeter.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByParentId(Long tweetId);

}
