package com.example.intermediate.repository;

import com.example.intermediate.domain.LikeComment;
import com.example.intermediate.domain.LikeSubComment;
import com.example.intermediate.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LikeSubCommentRepository extends JpaRepository<LikeSubComment, Long> {
    Long countAllBySubCommentId(Long subCommentId);

    void deleteBySubCommentId(Long subCommentId);

//    List<LikeSubComment> findAllByMember(Member member);
}
