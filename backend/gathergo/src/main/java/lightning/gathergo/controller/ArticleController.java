package lightning.gathergo.controller;

import lightning.gathergo.dto.CommonResponseDTO;
import lightning.gathergo.dto.GatheringDto;
import lightning.gathergo.dto.CommentDto;
import lightning.gathergo.mapper.ArticleMapper;
import lightning.gathergo.mapper.CommentMapper;
import lightning.gathergo.model.Article;
import lightning.gathergo.model.Comment;
import lightning.gathergo.model.Session;
import lightning.gathergo.model.User;
import lightning.gathergo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RequestMapping(value = "/articles", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ArticleController {
    private final ArticleService articleService;
    private final SessionService sessionService;
    private final UserService userService;
    private final CountService countService;
    private final CommentService commentService;
    private final RegionService regionService;
    private final ArticleMapper articleMapper;
    private final CommentMapper commentMapper;
    private final FcmMessagingService messagingService;

    @Autowired
    ArticleController(ArticleService articleService, SessionService sessionService, UserService userService, CountService countService,
                      CommentService commentService, RegionService regionService, ArticleMapper articleMapper, CommentMapper commentMapper, FcmMessagingService messagingService) {
        this.articleService = articleService;
        this.sessionService = sessionService;
        this.userService = userService;
        this.countService = countService;
        this.commentService = commentService;
        this.regionService = regionService;
        this.articleMapper = articleMapper;
        this.commentMapper = commentMapper;
        this.messagingService = messagingService;
    }

    // 게시물 리스트 검색
    @GetMapping
    ResponseEntity<CommonResponseDTO<?>> getArticles(@RequestParam Map<String, String> queryParam){
        GatheringDto.ArticleListResponse data;
        List<Article> articles = new ArrayList<>();
        Integer regionId = Integer.parseInt(queryParam.get("regionId"));
        Integer categoryId = Integer.parseInt(queryParam.get("categoryId"));
        String keyword = queryParam.get("keyword");

        articles = articleService.getArticlesByRegionAndCategoryAndKeyword(regionId, categoryId, keyword);
        data = new GatheringDto.ArticleListResponse();
        data.setArticles(articleMapper.toArticlePartialDtoList(articles));

        Map<String, Integer> countMap = countService.getCounts();
        data.getArticles().forEach(articlePartialDto -> {
            articlePartialDto.setCurr(countMap.get(articlePartialDto.getUuid()));
        });

        return new ResponseEntity<> (new CommonResponseDTO<>(1, "조회 성공", data), HttpStatus.OK);
    }

    // 마감임박 게시물
    @GetMapping("/orderby")
    ResponseEntity<CommonResponseDTO<?>> getImminentArticles(@RequestParam Map<String, String> queryParam){
        GatheringDto.ArticleListResponse data;
        List<Article> articles = new ArrayList<>();
        Integer regionId = Integer.parseInt(queryParam.get("regionId"));
        Integer categoryId = Integer.parseInt(queryParam.get("categoryId"));

        articles = articleService.getImminentArticles(regionId, categoryId);
        data = new GatheringDto.ArticleListResponse();
        data.setArticles(articleMapper.toArticlePartialDtoList(articles));

        Map<String, Integer> countMap = countService.getCounts();
        data.getArticles().forEach(articlePartialDto -> {
            articlePartialDto.setCurr(countMap.get(articlePartialDto.getUuid()));
        });

        return new ResponseEntity<> (new CommonResponseDTO<>(1, "마감임박 게시물 조회 성공", data), HttpStatus.OK);
    }

    // 게시물 작성
    @PostMapping
    ResponseEntity<?> createArticle(
            @RequestBody GatheringDto.CreateRequest request,
            @CookieValue(name = "sessionId") String sessionId
    ){
        articleService.validationCheckOn(request);
        String regionName = request.getLocation().split(" ")[0];
        articleService.mergeLocation(request);
        Article article = articleMapper.toArticle(request);
        Session session = sessionService.findSessionBySID(sessionId).get();

        article.setHostId(userService.findUserByUserId(session.getUserId()).get().getId());
        article.setRegionId(regionService.getRegionByName(regionName).get().getId());
        article = articleService.addArticle(article);

        countService.createCount(article.getUuid(), 1);

        return ResponseEntity.ok()
                .body(new CommonResponseDTO<String>(
                        1,
                        "작성 성공",
                        article.getUuid()
                ));
    }

    // 게시물 상세 조회
    @GetMapping("/{articleUuid}")
    ResponseEntity<?> getArticle(@PathVariable String articleUuid, @CookieValue(name = "sessionId", required = false) String sessionId){
        GatheringDto.ArticleDetailResponse data = new GatheringDto.ArticleDetailResponse();
        Integer currCount;
        List<Comment> comments;
        User user = new User();
        Session session;
        String userId = new String();

        if(null != sessionId){
            session = sessionService.findSessionBySID(sessionId).get();
            userId = session.getUserId();
        }

        // 게시물 디비에서 얻어오기
        Article article = articleService.getArticleByUuid(articleUuid);
        // 게시물에 달린 댓글 디비에서 얻어오기
        comments = articleService.getCommentsByUuid(articleUuid);
        List<CommentDto.Response> commentsDto = commentMapper.toCommentResponseList(comments);
        commentsDto.forEach(commentDto->{
            commentDto.setUserId(userService.findUserById(Integer.parseInt(commentDto.getUserId())).get().getUserId());
        });
        // 유저 정보 얻어오기
        user = articleService.getUserInfoByFromArticle(article.getUuid());

        data.setArticle(articleMapper.toArticleFullDto(article));
        data.setComments(commentsDto);
        data.setHost(new GatheringDto.UserDto(user.getUserId(), user.getIntroduction(), user.getUuid()));
        articleService.splitLocation(data);

        if(!userId.isBlank()){
            articleService.setHasJoinedAndIsHost(data, userId);
            commentService.setIsMyComment(data, userId);
        }

        currCount = countService.getCount(articleUuid);
        data.getArticle().setCurr(currCount);

        return ResponseEntity.ok()
                .body(new CommonResponseDTO<GatheringDto.ArticleDetailResponse>(
                                1,
                                "조회 성공",
                                data
                        )
                );
    }

    // 게시물 수정
    @PutMapping("/{articleUuid}")
    ResponseEntity<?> updateArticle(@PathVariable String articleUuid,
                                    @RequestBody GatheringDto.UpdateRequest request,
                                    @CookieValue(name = "sessionId") String sessionId
    ){
        articleService.validationCheckOn(request);
        articleService.mergeLocation(request);
        Session session;
        String userId = new String();
        session = sessionService.findSessionBySID(sessionId).get();
        userId = session.getUserId();

        Article replacement = articleMapper.toArticle(request); replacement.setUuid(articleUuid);
        String regionName = request.getLocation().split(" ")[0];
        replacement.setRegionId(regionService.getRegionByName(regionName).get().getId());

        articleService.updateArticle(articleUuid, replacement);

        GatheringDto.MessageResponse data = new GatheringDto.MessageResponse();
        data.setMessage("수정에 성공했습니다.");
        data.setArticleUuid(articleUuid);

        // 알림 발송
        sendNotification(articleUuid, "참여한 모임 정보가 변경되었습니다.");

        return ResponseEntity.ok()
                .body(new CommonResponseDTO<GatheringDto.MessageResponse>(
                                1,
                                "게시물 수정 성공",
                                data
                        )
                );
    }

    // 게시물 닫기(soft delete)
    @PutMapping("/{articleUuid}/close")
    ResponseEntity<?> closeArticle(@PathVariable String articleUuid, @CookieValue(name = "sessionId") String sessionId){
        sessionService.findSessionBySID(sessionId);

        articleService.setClosed(articleUuid);

        GatheringDto.MessageResponse data = new GatheringDto.MessageResponse();
        data.setMessage("수정에 성공했습니다.");
        data.setArticleUuid(articleUuid);

        // 닫기 알림 발송
        sendNotification(articleUuid, "호스트가 게시글을 마감했습니다.");

        // 구독 정보 삭제
        messagingService.deleteTopicAndDeviceTokens(articleUuid);

        return ResponseEntity.ok()
                .body(new CommonResponseDTO<GatheringDto.MessageResponse>(
                                1,
                                "게시물 닫기 성공",
                                data
                        )
                );
    }

    // 댓글 생성
    @PostMapping("/{articleUuid}/comments")
    ResponseEntity<?> createComment(@PathVariable String articleUuid, @RequestBody CommentDto.CreateRequest request, @CookieValue(name = "sessionId") String sessionId){
        Comment comment = commentMapper.toComment(request);
        Session session = sessionService.findSessionBySID(sessionId).get();
        comment.setUserId(userService.findUserByUserId(session.getUserId()).get().getId());

        articleService.addComment(comment, articleUuid);

        // 메시지 발송
        sendNotification(articleUuid, session.getUserName() + "님의 댓글이 추가되었습니다.");

        return ResponseEntity.ok()
                .body(new CommonResponseDTO<String>(
                                1,
                                "댓글 작성 성공",
                                ""
                        )
                );
    }

    // 댓글 수정
    @PutMapping("/{articleUuid}/comments/{commentUuid}")
    ResponseEntity<?> updateComment(@PathVariable("articleUuid") String articleUuid, @PathVariable("commentUuid") String commentUuid,
                                    @RequestBody CommentDto.UpdateReqeust request, @CookieValue(name = "sessionId") String sessionId){
        Comment replacement = commentMapper.toComment(request);
        Session session = sessionService.findSessionBySID(sessionId).get();
        replacement.setUserId(userService.findUserByUserId(session.getUserId()).get().getId());
        replacement.setUuid(commentUuid);

        articleService.updateComment(replacement);

        return ResponseEntity.ok()
                .body(new CommonResponseDTO<String>(
                                1,
                                "댓글 수정 성공",
                                ""
                        )
                );
    }

    // 댓글 삭제
    @DeleteMapping("/{articleUuid}/comments/{commentUuid}")
    ResponseEntity<?> deleteComment(@PathVariable("articleUuid") String articleUuid, @PathVariable("commentUuid") String commentUuid,
                                    @CookieValue(name = "sessionId") String sessionId){
        Session session = sessionService.findSessionBySID(sessionId).get();


        articleService.deleteComment(commentUuid);

        return ResponseEntity.ok()
                .body(new CommonResponseDTO<String>(
                                1,
                                "댓글 삭제 성공",
                                ""
                        )
                );
    }

    // 참가
    @PutMapping("/{articleUuid}/users")
    ResponseEntity<?> joinArticle(@PathVariable("articleUuid") String articleUuid, @CookieValue(name = "sessionId") String sessionId){
        GatheringDto.MessageResponse data = new GatheringDto.MessageResponse();
        Integer currCount;
        Session session = sessionService.findSessionBySID(sessionId).get();
        String userId = session.getUserId();

        articleService.addGuest(userId, articleUuid);

        data.setArticleUuid(articleUuid);
        data.setMessage("참가에 성공했습니다.");

        return ResponseEntity.ok()
                .body(new CommonResponseDTO<GatheringDto.MessageResponse>(
                                1,
                                "게시물 참가 성공",
                                data
                        )
                );
    }

    // 나가기
    @DeleteMapping("/{articleUuid}/users")
    ResponseEntity<?> exitArticle(@PathVariable("articleUuid") String articleUuid, @CookieValue(name = "sessionId") String sessionId){
        GatheringDto.MessageResponse data = new GatheringDto.MessageResponse();
        Integer currCount;
        Session session = sessionService.findSessionBySID(sessionId).get();
        String userId = session.getUserId();
        Map<String, Object> response = new HashMap<>();
        articleService.deleteGuest(userId, articleUuid);

        data.setArticleUuid(articleUuid);
        data.setMessage("모임 나가기에 성공했습니다.");

        return ResponseEntity.ok()
                .body(new CommonResponseDTO<GatheringDto.MessageResponse>(
                                1,
                                "게시물 나가기 성공",
                                data
                        )
                );
    }

    private void sendNotification(String articleUuid, String body) {
        Article article = articleService.getArticleByUuid(articleUuid);

        Map<String, String> payload = Map.ofEntries(Map.entry("title", article.getTitle()), Map.entry("body", body));

        // 비동기 요청
        messagingService.sendMessageToTopic(articleUuid, payload);
    }

}

