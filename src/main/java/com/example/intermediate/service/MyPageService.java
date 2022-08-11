package com.example.intermediate.service;

import com.example.intermediate.controller.response.*;

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
public class MyPageService {
    private final CommentRepository commentRepository;
    private final TokenProvider tokenProvider;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    private final SubCommentRepository subCommentRepository;
    private final LikePostRepository likePostRepository;
    private final LikeCommentRepository likeCommentRepository;
    private final LikeSubCommentRepository likeSubCommentRepository;
    private LikeComment likecomment;

    @Transactional
    public ResponseDto<MyPageDto> getMyPost(HttpServletRequest request) {
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

        List<Comment> commentList = commentRepository.findByMember(member);
        List<MyPageCommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for (Comment comment : commentList) {
            commentResponseDtoList.add(
                    MyPageCommentResponseDto.builder()
                            .idi(comment.getId())
                            .author(comment.getMember().getNickname())
                            .content(comment.getContent())
                            .likes(comment.getLikes()) // 여기에 likes
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .build()
            );
        }

        List<SubComment> subCommentList = subCommentRepository.findAllByMember(member);
        List<MyPageSubCommentResponseDto> subCommentResponseDtoList = new ArrayList<>();

        for (SubComment subComment : subCommentList) {
            subCommentResponseDtoList.add(
                    MyPageSubCommentResponseDto.builder()
                            .id(subComment.getId())
                            .author(subComment.getMember().getNickname())
                            .content(subComment.getContent())
                            .likes(subComment.getLikes())
                            .createdAt(subComment.getCreatedAt())
                            .modifiedAt(subComment.getModifiedAt())
                            .build()
            );
        }

        List<Post> postList = postRepository.findByMember(member);
        List<MyPagePostResponseDto> postResponseDtoList = new ArrayList<>();

        for (Post post : postList) {
            postResponseDtoList.add(
                    MyPagePostResponseDto.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .author(post.getMember().getNickname())
                            .likes(post.getLikes()) // 여기에 likes
                            .createdAt(post.getCreatedAt())
                            .modifiedAt(post.getModifiedAt())
                            .build()
            );
        }
        MyPageDto myPageDto = new MyPageDto();
        myPageDto.update(postResponseDtoList, commentResponseDtoList,  subCommentResponseDtoList);
        return ResponseDto.success(  myPageDto  );
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

    ////////////// △내가 쓴 것 △ //////////////////   ▽ 좋아요 누른 것만 ▽///////////////

    @Transactional
    public ResponseDto<MyLikePageDto> getMyLikePost(HttpServletRequest request) {
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


        List<LikeComment> likeCommentList = likeCommentRepository.findAllLikeByMember(member);
        List<LikeCommentResponseDto> LikeCommentResponseDtoList = new ArrayList<>();

        for (LikeComment likeComment : likeCommentList) {
            LikeCommentResponseDtoList.add(
                    LikeCommentResponseDto.builder()
                            .id(likeComment.getId())
                            .author(likeComment.getMember().getNickname())
                            .content(likeComment.getContent())
                            .likes(likeComment.getLikes())
                            .createdAt(likeComment.getCreatedAt())
                            .modifiedAt(likeComment.getModifiedAt())
                            .build()
            );

//            List<LikeSubComment> likeSubCommentList = likeSubCommentRepository.findAllByMember(member);
//            List<MyPageLikeSubCommentResponseDto> MyPageLikeSubCommentResponseDtoList = new ArrayList<>();
//
//            for (LikeSubComment likeSubComment : likeSubCommentList) {
//                MyPageLikeSubCommentResponseDtoList.add(
//                        MyPageLikeSubCommentResponseDto.builder()
//                                .id(likeSubComment.getId())
//                                .author(likeSubComment.getMember().getNickname())
//                                .content(likeSubComment.getContent())
//                                .likes(likeSubComment.getLikes())
//                                .createdAt(likeSubComment.getCreatedAt())
//                                .modifiedAt(likeSubComment.getModifiedAt())
//                                .build()
//                );
//            }
        }


        List<LikePost> likePostList = likePostRepository.getByMember(member);
        List<MyPageLikePostResponseDto> MyPageLikePostResponeseDtoList = new ArrayList<>();
        for(LikePost likePost : likePostList) {
            MyPageLikePostResponeseDtoList.add(
                    MyPageLikePostResponseDto.builder()
                    .id(likePost.getId())
                    .title(likePost.getTitle())
                    .content(likePost.getContent())
                    .author(likePost.getMember().getNickname())
                    .likes(likePost.getLikes()) // 여기에 likes
                    .createdAt(likePost.getCreatedAt())
                    .modifiedAt(likePost.getModifiedAt())
                    .build()
            );
        }
        MyLikePageDto myLikePageDto = new MyLikePageDto();
        myLikePageDto.update(MyPageLikePostResponeseDtoList, LikeCommentResponseDtoList);
        return ResponseDto.success(  myLikePageDto  );
    }
}

