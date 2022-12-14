package com.example.intermediate.domain;

import com.example.intermediate.controller.request.CommentRequestDto;
import com.example.intermediate.controller.request.SubCommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SubComment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "post_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @JoinColumn(name = "comment_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private Long likes;

    @OneToMany(mappedBy = "subComment", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<LikeSubComment> likeSubComment;
    public void update(SubCommentRequestDto subCommentRequestDto) {
        this.content = subCommentRequestDto.getContent();
    }

    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }

    public void updateLikes(Long likes) {
        this.likes = likes;
    }
}
