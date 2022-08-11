package com.example.intermediate.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyLikePageDto {
    private List likePostList;
    private List likeCommentList;
//    private List likeSubCommentList;


    public void update(
            List<MyPageLikePostResponseDto>LikePostResponseDtoList,
            List<LikeCommentResponseDto> LikeCommentResponseDtoList) {
        this.likePostList = LikePostResponseDtoList;
        this.likeCommentList = LikeCommentResponseDtoList;
//        this.likeSubCommentList = LikeSubCommentResponseDtoList;
    }
}
