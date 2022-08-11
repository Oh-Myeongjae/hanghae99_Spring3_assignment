package com.example.intermediate.controller;

import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;

    @RequestMapping(value = "/api/myPage", method = RequestMethod.POST)
    ResponseDto<?> getMyPost( HttpServletRequest request) {
        return myPageService.getMyPost(request);
    }


    @RequestMapping(value = "/api/myPage/like", method = RequestMethod.POST)
    public ResponseDto<?> getMyLikePost(HttpServletRequest request) {
        return myPageService.getMyLikePost(request);
    }
}