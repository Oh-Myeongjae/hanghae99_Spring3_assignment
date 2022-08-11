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
public class MyPageSubCommentResponseDto {
    private Long id;
    private Long likes;
    private String content;
    private String author;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
