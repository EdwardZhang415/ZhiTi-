package com.upic.controller;

import com.upic.enums.TheSubmitStatusEnum;
import com.upic.po.Comment;
import com.upic.po.TheSolution;
import com.upic.po.TheSubmit;
import com.upic.po.User;
import com.upic.service.CommentService;
import com.upic.service.TheSolutionService;
import com.upic.service.TheSubmitSerice;
import com.upic.service.UserService;
import com.upic.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/TheSubmit")
public class TheSubmitController {
    @Autowired
    private TheSubmitSerice theSubmitSerice;
    @Autowired
    private TheSolutionService theSolutionService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    /**
     * 获取提交记录
     *
     * @param questionId
     * @param pageable
     * @return
     */
    @GetMapping("getSubmits")
    @ApiOperation("获取提交记录")
    public Page<TheSubmit> getSubmits(long questionId, Pageable pageable, HttpSession session) {
        try {

            long userId = ((User) session.getAttribute("user")).getId();
            Sort sort = new Sort(Sort.Direction.DESC, "id");
            List<TheSubmit> theSubmits = theSubmitSerice.findByUserIdAndQuestionId(userId, questionId, sort);
            return new PageImpl<>(theSubmits, pageable, theSubmits.size());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取所有题解
     *
     * @param questionId
     * @param pageable
     * @return
     */
    @GetMapping("getTheSolutions")
    @ApiOperation("获取所有题解")
    public Page<TheSolution> getTheSolutions(long questionId, Pageable pageable) {
        try {
            return theSolutionService.findByQuestionId(questionId, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取题解作者名
     *
     * @param theSolutionId
     * @return
     */
    @GetMapping("getTheSolutionAuthor")
    @ApiOperation("获取题解作者名")
    public String getTheSolutionAuthor(long theSolutionId) {
        try {
            long id = theSolutionService.findOne(theSolutionId).getUserId();
            return userService.findOne(id).getUsername();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取所有评论
     *
     * @param theSolutionId
     * @param pageable
     * @return
     */
    @GetMapping("getComments")
    @ApiOperation("获取所有评论")
    public Page<Comment> getComments(long theSolutionId, Pageable pageable) {
        try {
            return commentService.findByTheSolutionId(theSolutionId, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取评论作者名
     *
     * @param theCommentId
     * @return
     */
    @GetMapping("getTheCommentAuthor")
    @ApiOperation("获取题解作者名")
    public String getTheCommentAuthor(long theCommentId) {
        try {
            long id = commentService.findOne(theCommentId).getUserId();
            return userService.findOne(id).getUsername();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查看提交详情
     *
     * @param theSubmitId
     * @return
     */
    public TheSubmitStatusEnum getSubmitDetail(long theSubmitId) {
        try {
            return theSubmitSerice.findOne(theSubmitId).getStatus();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 发布题解
     */
    @PostMapping("/submitSolution")
    public Result submitSolution(@RequestParam("questionId") Long questionId,
                                 @RequestParam("code") String code,
                                 HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            return new Result(1, "用户未登陆", null);
        }
        Long id = user.getId();
        TheSolution theSolution = new TheSolution();
        theSolution.setCode(code);
        theSolution.setQuestionId(questionId);
        theSolution.setUserId(id);
        theSolution.setCreateTime(new Date());
        theSolutionService.saveTheSolution(theSolution);
        return new Result(0, "成功", null);
    }

    /**
     * 发布题解评论
     */
    @PostMapping("/submitSolutionComment")
    public Result submitSolutionComment(@RequestParam("solutionId") Long solutionId,
                                        @RequestParam("content") String content,
                                        HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            return new Result(1, "用户未登陆", null);
        }
        Comment comment = new Comment();
        comment.setComment(content);
        comment.setTheSolutionId(solutionId);
        comment.setUserId(user.getId());
        comment.setCreateTime(new Date());
        commentService.saveComment(comment);
        return new Result(0, "成功", null);
    }
}
