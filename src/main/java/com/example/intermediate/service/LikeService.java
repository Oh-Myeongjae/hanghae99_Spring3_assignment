package com.example.intermediate.service;

import com.example.intermediate.controller.response.CommentResponseDto;
import com.example.intermediate.controller.response.LikeResponseDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.controller.response.SubCommentResponseDto;
import com.example.intermediate.domain.*;
import com.example.intermediate.jwt.TokenProvider;
import com.example.intermediate.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikePostRepository likePostRepository;
    private final LikeCommentRepository likeCommentRepository;
    private final LikeSubCommentRepository likeSubCommentRepository;
    private final TokenProvider tokenProvider;
    private final PostService postService;
    private final CommentRepository commentRepository;
    private final SubCommentRepository subCommentRepository;
    private final CommentService commentService;
    private final SubCommentService subCommentService;


    public ResponseDto<?> likePost(Long postId, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");

        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = postService.isPresentPost(postId);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        // like 등록
        LikePost likePost = LikePost.builder()
                .member(member)
                .post(post)
                .build();
        likePostRepository.save(likePost);

        // 해당 게시물 likes 업데이트
        Long likes = likePostRepository.countAllByPostId(post.getId());

        post.updateLikes(likes);

        // 댓글 리스트의 Dto화
        List<Comment> commentList = commentRepository.findAllByPost(post);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for (Comment comment : commentList) {

            // 대댓글 리스트의 Dto화
            List<SubComment> subCommentList = subCommentRepository.findAllByComment(comment);
            List<SubCommentResponseDto> subCommentResponseDtoList = new ArrayList<>();

            for (SubComment subComment : subCommentList) {
                subCommentResponseDtoList.add(
                        SubCommentResponseDto.builder()
                                .id(subComment.getId())
                                .author(subComment.getMember().getNickname())
                                .content(subComment.getContent())
                                .likes(subComment.getLikes()) // 여기에 likes
                                .createdAt(subComment.getCreatedAt())
                                .modifiedAt(subComment.getModifiedAt())
                                .build()
                );
            }

            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .id(comment.getId())
                            .author(comment.getMember().getNickname())
                            .content(comment.getContent())
                            .subCommentResponseDtoList(subCommentResponseDtoList) // 여기에 대댓글 넣기
                            .likes(comment.getLikes()) // 여기에 likes
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .build()
            );
        }

        // 게시글 Dto
        LikeResponseDto likeResponseDto = LikeResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getMember().getNickname())
                .commentResponseDtoList(commentResponseDtoList) // 여기에 댓글 Dto 리스트
                .likes(post.getLikes()) // 여기에 likes
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();

        return ResponseDto.success(likeResponseDto);
    }

    public ResponseDto<?> likeComment(Long commentId, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Comment comment = commentService.isPresentComment(commentId);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        // like 등록
        LikeComment likeComment = LikeComment.builder()
                .member(comment.getMember())
                .content(comment.getContent())
                .build();
        likeCommentRepository.save(likeComment);

        // 해당 댓글 likes 업데이트
        Long likes = likeCommentRepository.countAllByCommentId(comment.getId());
        comment.updateLikes(likes);

        List<SubComment> subCommentList = subCommentRepository.findAllByComment(comment);
        List<SubCommentResponseDto> subCommentResponseDtoList = new ArrayList<>();

        for (SubComment subComment : subCommentList) {
            subCommentResponseDtoList.add(
                    SubCommentResponseDto.builder()
                            .id(subComment.getId())
                            .author(subComment.getMember().getNickname())
                            .content(subComment.getContent())
                            .likes(subComment.getLikes()) // 여기에 likes
                            .createdAt(subComment.getCreatedAt())
                            .modifiedAt(subComment.getModifiedAt())
                            .build()
            );
        }

        CommentResponseDto commentResponseDto = CommentResponseDto.builder()
                .id(comment.getId())
                .author(comment.getMember().getNickname())
                .content(comment.getContent())
                .subCommentResponseDtoList(subCommentResponseDtoList) // 여기에 대댓글 리스트 Dto
                .likes(comment.getLikes()) // 여기에 likes
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
        return ResponseDto.success(commentResponseDto);
    }

    public ResponseDto<?> likeSubComment(Long subCommentId, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        SubComment subComment = subCommentService.isPresentSubComment(subCommentId);
        if (null == subComment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        // like 등록
        LikeSubComment likeSubComment = LikeSubComment.builder()
                .member(subComment.getMember())
                .subComment(subComment)
                .build();
        likeSubCommentRepository.save(likeSubComment);

        // 해당 대댓글 likes 업데이트
        Long likes = likeSubCommentRepository.countAllBySubCommentId(subComment.getId());
        subComment.updateLikes(likes);

        SubCommentResponseDto subCommentResponseDto = SubCommentResponseDto.builder()
                .id(subComment.getId())
                .author(subComment.getMember().getNickname())
                .content(subComment.getContent())
                .likes(subComment.getLikes()) // 여기에 likes
                .createdAt(subComment.getCreatedAt())
                .modifiedAt(subComment.getModifiedAt())
                .build();

        return ResponseDto.success(subCommentResponseDto);
    }

    public ResponseDto<?> unlikePost(Long postId, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = postService.isPresentPost(postId);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        // 해당 게시물의 like 수
        Long likesTotal = likePostRepository.countAllByPostId(post.getId());
        if (likesTotal != 0) {
            // like 삭제
            likePostRepository.deleteById(likesTotal);
        }
        // like 업데이트
        Long likes = likePostRepository.countAllByPostId(post.getId());
        post.updateLikes(likes);

        // 댓글 리스트의 Dto화
        List<Comment> commentList = commentRepository.findAllByPost(post);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for (Comment comment : commentList) {

            // 대댓글 리스트의 Dto화
            List<SubComment> subCommentList = subCommentRepository.findAllByComment(comment);
            List<SubCommentResponseDto> subCommentResponseDtoList = new ArrayList<>();

            for (SubComment subComment : subCommentList) {
                subCommentResponseDtoList.add(
                        SubCommentResponseDto.builder()
                                .id(subComment.getId())
                                .author(subComment.getMember().getNickname())
                                .content(subComment.getContent())
                                .likes(subComment.getLikes()) // 여기에 likes
                                .createdAt(subComment.getCreatedAt())
                                .modifiedAt(subComment.getModifiedAt())
                                .build()
                );
            }

            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .id(comment.getId())
                            .author(comment.getMember().getNickname())
                            .content(comment.getContent())
                            .subCommentResponseDtoList(subCommentResponseDtoList) // 여기에 대댓글 넣기
                            .likes(comment.getLikes()) // 여기에 likes
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .build()
            );
        }

        // 게시글 Dto
        LikeResponseDto likeResponseDto = LikeResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getMember().getNickname())
                .commentResponseDtoList(commentResponseDtoList) // 여기에 댓글 Dto 리스트
                .likes(post.getLikes()) // 여기에 likes
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();

        return ResponseDto.success(likeResponseDto);
    }

    public ResponseDto<?> unlikeComment(Long commentId, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Comment comment = commentService.isPresentComment(commentId);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        // 해당 댓글의 like 수
        Long likesTotal = likeCommentRepository.countAllByCommentId(comment.getId());
        if (likesTotal != 0) {
            // like 삭제
            likeCommentRepository.deleteById(likesTotal);
        }
        // like 업데이트
        Long likes = likeCommentRepository.countAllByCommentId(comment.getId());
        comment.updateLikes(likes);

        List<SubComment> subCommentList = subCommentRepository.findAllByComment(comment);
        List<SubCommentResponseDto> subCommentResponseDtoList = new ArrayList<>();

        for (SubComment subComment : subCommentList) {
            subCommentResponseDtoList.add(
                    SubCommentResponseDto.builder()
                            .id(subComment.getId())
                            .author(subComment.getMember().getNickname())
                            .content(subComment.getContent())
                            .likes(subComment.getLikes()) // 여기에 likes
                            .createdAt(subComment.getCreatedAt())
                            .modifiedAt(subComment.getModifiedAt())
                            .build()
            );
        }

        CommentResponseDto commentResponseDto = CommentResponseDto.builder()
                .id(comment.getId())
                .author(comment.getMember().getNickname())
                .content(comment.getContent())
                .subCommentResponseDtoList(subCommentResponseDtoList) // 여기에 대댓글 리스트 Dto
                .likes(comment.getLikes()) // 여기에 likes
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
        return ResponseDto.success(commentResponseDto);
    }

    public ResponseDto<?> unlikeSubComment(Long subCommentId, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        SubComment subComment = subCommentService.isPresentSubComment(subCommentId);
        if (null == subComment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        // 해당 대댓글의 like 수
        Long likesTotal = likeSubCommentRepository.countAllBySubCommentId(subComment.getId());
        if (likesTotal != 0) {
            // like 삭제
            likeSubCommentRepository.deleteById(likesTotal);
        }
        // like 업데이트
        Long likes = likeSubCommentRepository.countAllBySubCommentId(subComment.getId());
        subComment.updateLikes(likes);

        SubCommentResponseDto subCommentResponseDto = SubCommentResponseDto.builder()
                .id(subComment.getId())
                .author(subComment.getMember().getNickname())
                .content(subComment.getContent())
                .likes(subComment.getLikes()) // 여기에 likes
                .createdAt(subComment.getCreatedAt())
                .modifiedAt(subComment.getModifiedAt())
                .build();

        return ResponseDto.success(subCommentResponseDto);
    }


    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
