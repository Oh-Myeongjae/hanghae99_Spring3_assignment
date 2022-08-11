package com.example.intermediate.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageLikeSubCommentResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private Long likes;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
