package com.example.intermediate.controller;


import com.example.intermediate.controller.request.SubCommentRequestDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.domain.UserDetailsImpl;
import com.example.intermediate.service.SubCommentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Validated
@RequiredArgsConstructor
@RestController
public class SubCommentController {

    private final SubCommentService subCommentService;

    @RequestMapping(value = "/api/auth/subcomment", method = RequestMethod.POST)
    public ResponseDto<?> createSubComment(@RequestBody SubCommentRequestDto requestDto,
                                           HttpServletRequest request) {
        return subCommentService.createSubComment(requestDto, request);
    }

    @RequestMapping(value = "/api/subcomment/{id}", method = RequestMethod.GET)
    public ResponseDto<?> getAllSubComments(@PathVariable Long id) {
        return subCommentService.getAllSubCommentsByComment(id);
    }

    @RequestMapping(value = "/api/auth/subcomment/{id}", method = RequestMethod.PUT)
    public ResponseDto<?> updateSubComment(@PathVariable Long id, @RequestBody SubCommentRequestDto requestDto,
                                           HttpServletRequest request) {
        return subCommentService.updateSubComment(id, requestDto, request);
    }

    @RequestMapping(value = "/api/auth/subcomment/{id}", method = RequestMethod.DELETE)
    public ResponseDto<?> deleteComment(@PathVariable Long id, HttpServletRequest request) {
        return subCommentService.deleteComment(id, request);
    }

//    @RequestMapping(value = "/api/auth/like/subComment/{id}", method = RequestMethod.GET)
//    public ResponseDto<?> getAllSubComment(@AuthenticationPrincipal UserDetailsImpl member_id, Long subCommentId) {
//        return  subCommentService.getAllSubComment(member_id, subCommentId);
//    }
}
