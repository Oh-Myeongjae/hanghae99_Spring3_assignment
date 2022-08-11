package com.example.intermediate.domain;

import com.example.intermediate.controller.request.CommentRequestDto;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "member_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  @JoinColumn(name = "post_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Post post;

  @OneToMany(mappedBy ="comment", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<SubComment> subComments;

  @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<LikeComment> likeSubComment;
  @Column(nullable = false)
  private String content;

  @Column(nullable = true)
  private Long likes;

  public void update(CommentRequestDto commentRequestDto) {
    this.content = commentRequestDto.getContent();
  }

  public boolean validateMember(Member member) {
    return !this.member.equals(member);
  }

  public void updateLikes(Long likes) {
    this.likes = likes;
  }
}
