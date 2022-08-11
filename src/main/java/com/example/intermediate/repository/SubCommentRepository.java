package com.example.intermediate.repository;

import com.example.intermediate.domain.Comment;
import com.example.intermediate.domain.SubComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubCommentRepository extends JpaRepository<SubComment, Long> {
    List<SubComment> findAllByComment(Comment comment);

    void deleteByPostId(Long id);
}
