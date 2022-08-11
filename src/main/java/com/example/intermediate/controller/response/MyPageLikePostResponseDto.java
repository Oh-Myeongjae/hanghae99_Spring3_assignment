package com.example.intermediate.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageLikePostResponseDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    private Long likes;
    private List<CommentResponseDto> commentResponseDtoList;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
