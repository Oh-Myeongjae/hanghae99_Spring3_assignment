package com.example.intermediate.repository;

import com.example.intermediate.controller.response.LikeResponseDto;
import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.Post;
import java.util.List;
import java.util.Optional;

import com.example.intermediate.domain.UserDetailsImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
  List<Post> findAllByOrderByModifiedAtDesc();

    Optional<Post> findByMemberId(Member member);
    List<Post> findByMember(Member member);
}
