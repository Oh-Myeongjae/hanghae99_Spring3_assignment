package com.example.intermediate.domain;

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
public class MyLikePage extends Timestamped{
    @Id
    @Column(name = "likeMypage_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JoinColumn(name = "member_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;


    @JoinColumn(name = "likePost_id", nullable = false)
    @ManyToMany(fetch = FetchType.LAZY)
    private List<LikePost> likePostList;


    @JoinColumn(name = "likeComment_id", nullable = false)
    @ManyToMany(fetch = FetchType.LAZY)
    private List<LikeComment> likeCommentList;

//    @JoinColumn(name = "likeSubcomment_id", nullable = false)
//    @ManyToMany(fetch = FetchType.LAZY)
//    private List<LikeSubComment> likeSubCommentList;

    public void update(Member member,
                       List<LikePost> MyPageLikePostResponeseDtoList,
                       List<LikeComment> LikeCommentResponseDtoList) {
        this.member = member;
        this.likePostList = MyPageLikePostResponeseDtoList;
        this.likeCommentList = LikeCommentResponseDtoList;
//        this.likeSubCommentList = MyPageLikeSubCommentResponseDtoList;

    }
}
