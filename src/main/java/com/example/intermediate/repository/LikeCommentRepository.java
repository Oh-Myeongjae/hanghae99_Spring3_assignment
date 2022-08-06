package com.example.intermediate.repository;

import com.example.intermediate.domain.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    Long countAllByCommentId(Long commentId);

    void deleteByCommentId(Long commentId);
}
