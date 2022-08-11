package com.example.intermediate.repository;

import com.example.intermediate.domain.LikePost;
import com.example.intermediate.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    Long countAllByPostId(Long postId);

    void deleteByPostId(Long postId);

    List<LikePost> getByMember(Member member);

//    List<LikePost> findAllByLikePost(LikePost likePost);
}
