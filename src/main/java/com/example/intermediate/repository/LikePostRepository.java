package com.example.intermediate.repository;

import com.example.intermediate.domain.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    Long countAllByPostId(Long postId);

    void deleteByPostId(Long postId);
}
