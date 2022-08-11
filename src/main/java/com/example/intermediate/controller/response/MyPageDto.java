package com.example.intermediate.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MyPageDto {

    private List postList;
    private List commentList;
    private List subCommentList;


    public void update(
            List<MyPagePostResponseDto> postResponseDtoList,
            List<MyPageCommentResponseDto> commentResponseDtoList,
            List<MyPageSubCommentResponseDto> subCommentResponseDtoList) {
        this.postList = postResponseDtoList;
        this.commentList = commentResponseDtoList;
        this.subCommentList = subCommentResponseDtoList;

    }
}
