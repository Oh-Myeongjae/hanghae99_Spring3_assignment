package com.example.intermediate.repository;

import com.example.intermediate.domain.Comment;
import com.example.intermediate.domain.LikeComment;
import com.example.intermediate.domain.LikePost;
import com.example.intermediate.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    Long countAllByCommentId(Long commentId);

    void deleteByCommentId(Long commentId);

    List<LikeComment> findAllLikeByMember(Member member);
}
