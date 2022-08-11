package com.example.intermediate.repository;

import com.example.intermediate.domain.LikeComment;
import com.example.intermediate.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    Long countAllByCommentId(Long commentId);

    void deleteByCommentId(Long commentId);

    List<LikeComment> findAllLikeByMember(Member member);
}
