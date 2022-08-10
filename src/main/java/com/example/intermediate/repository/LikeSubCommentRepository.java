package com.example.intermediate.repository;

import com.example.intermediate.domain.LikeSubComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeSubCommentRepository extends JpaRepository<LikeSubComment, Long> {
    Long countAllBySubCommentId(Long subCommentId);

    void deleteBySubCommentId(Long subCommentId);

}
