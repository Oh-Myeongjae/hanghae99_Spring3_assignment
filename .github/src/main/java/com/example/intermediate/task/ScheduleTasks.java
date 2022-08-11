package com.example.intermediate.task;

import com.example.intermediate.domain.Comment;
import com.example.intermediate.domain.Post;
import com.example.intermediate.repository.CommentRepository;
import com.example.intermediate.repository.PostRepository;
import com.example.intermediate.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
public class ScheduleTasks {
//    private static final Logger log = LoggerFactory.getLogger(ScheduleTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//    @Scheduled(fixedRate = 1000, initialDelay = 1)
//    public void schedule1(){
//        log.info("1초에 한번씩 실행중;");
//    }
//
//    // 실행 시간, 초기 지연시간 지정으로 순서 설정
//    @Scheduled(fixedRate = 5000, initialDelay = 2)
//    public void schedule2(){
//        log.info("5초에 한번씩 실행중");
//        log.info(" 현재시간 : " + dateFormat.format(new Date()));
//    }
//
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostService postService;
    // corn 으로 초(0-59) 분(0-59) 시간(0-23) 일(1-31) 월(1-12) 요일(1-7)
    @Scheduled(cron="0 0 1 * * *")
    public void schedule3(){
        log.info("스케쥴러 작동 시작 ! 현재 시각 : "+dateFormat.format(new Date()));
        List<Post> allPost = postRepository.findAll();
        for(Post p : allPost){
            List<Comment> comments = commentRepository.findAllByPost(p);
            int count = comments.size();
            if(count == 0) {
                Long id = postService.scheduleDeletePost(p.getId());
                System.out.println(id+"번 게시글을 삭제 하였습니다.!!");
            }
        }
    }
}
