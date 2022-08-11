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
public class LikeCommentResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private List<CommentResponseDto> commentResponseDtoList;
    private Long likes;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
