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
public class MyPagePostResponseDto {
    private Long id;
    private String title;
    private String nickname;
    private String content;
    private Long likes;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
