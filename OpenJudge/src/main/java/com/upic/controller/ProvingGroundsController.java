package com.upic.controller;

import com.upic.enums.TheSubmitStatusEnum;
import com.upic.enums.UserThroughStatusEnum;
import com.upic.po.*;
import com.upic.service.*;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/theProvingGrounds")
public class ProvingGroundsController {
    @Autowired
    private ProvingGroundsService provingGroundsService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private UserThroughService userThroughService;
    @Autowired
    private MissionQuestionService missionQuestionService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private TheSubmitSerice theSubmitSerice;
    @Autowired
    private PreconditionService preconditionService;
    private static Logger logger = LoggerFactory.getLogger(UpdateQuestionController.class);

    /**
     * 获取试炼场
     *
     * @param pageable
     * @return
     */
    @GetMapping("getProvingGrounds")
    @ApiOperation("获取试炼场")
    public Page<ProvingGrounds> getProvingGrounds(Pageable pageable) {
        Page<ProvingGrounds> result;
        try {
            result = provingGroundsService.searchProvingGrounds(pageable);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据试炼场获取任务
     *
     * @param pageable
     * @param ProvingGroundsId
     * @return
     */
    @GetMapping("getMissionsByProvingGrounds")
    @ApiOperation("根据试炼场获取任务")
    public Page<Mission> getMissionsByProvingGrounds(long ProvingGroundsId, Pageable pageable) {
        try {
            Page<Mission> result = missionService.findByProvingGroundsId(ProvingGroundsId, pageable);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取完成进度
     *
     * @param missionId
     * @param pageable
     * @return
     */
    @GetMapping("getUserThroughStatus")
    @ApiOperation("获取完成进度")
    public Page<UserThrough> getUserThroughStatus(long missionId, Pageable pageable, HttpServletRequest request,
                                                  HttpServletResponse response, HttpSession session) {
        try {
            //long userId=1;
            long userId = ((User) session.getAttribute("user")).getId();
            Page<UserThrough> userThrough = userThroughService.findByUserIdAndMissionId(userId, missionId, pageable);
            return userThrough;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据任务获取题目
     *
     * @param missionId
     * @param pageable
     * @return
     */
    @GetMapping("getQuestionsByMission")
    @ApiOperation("根据任务获取题目")
    public Page<Question> getQuestionsByMission(long missionId, Pageable pageable, HttpServletRequest request,
                                                HttpServletResponse response, HttpSession session) {
        try {
            //long userId=1;
            List<MissionQuestion> missionQuestions = missionQuestionService.findByMissionId(missionId);
            logger.info(String.valueOf(missionQuestions.size()));
            List<Question> li = new ArrayList<>();
            for (MissionQuestion u : missionQuestions)
                li.add(questionService.findOne(u.getQuestionId()));
            //List<MissionQuestion> missionQuestions=missionQuestionService.findByMissionId(missionId);
            return new PageImpl<>(li, pageable, li.size());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取每道题的提交情况
     *
     * @param questionId
     * @param pageable
     * @return
     */
    @GetMapping("theSubmits")
    @ApiOperation("获取每道题的提交情况")
    public Page<TheSubmit> theSubmits(long questionId, Pageable pageable) {
        try {
            return theSubmitSerice.findByQuestionId(questionId, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 确认通过
     *
     * @param questionId
     * @return
     */
    @GetMapping("isPassed")
    @ApiOperation("确认通过")
    public Boolean isPassed(long questionId, HttpServletRequest request,
                            HttpServletResponse response, HttpSession session) {
        try {
            //long userId=1;
            long userId = ((User) session.getAttribute("user")).getId();
            List<TheSubmit> theSubmits;
            theSubmits = theSubmitSerice.findByUserIdAndQuestionId(userId, questionId);
            //logger.info(String.valueOf(questionId));
            //logger.info(String.valueOf(theSubmits.size()));
            return theSubmits.stream().anyMatch(theSubmit -> theSubmit.getStatus().equals(TheSubmitStatusEnum.ACCEPTED));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询先决条件
     *
     * @param missionId
     * @param pageable
     * @return
     */
    @GetMapping("getPreconditions")
    @ApiOperation("查询先决条件")
    public Page<Precondition> getPreconditions(long missionId, Pageable pageable) {
        try {
            return preconditionService.findByMissionId(missionId, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询是否已完成任务
     *
     * @param missionId
     * @param pageable
     * @return
     */
    @GetMapping("isFinished")
    @ApiOperation("查询是否已完成任务")
    public Boolean isFinished(long missionId, Pageable pageable, HttpServletRequest request,
                              HttpServletResponse response, HttpSession session) {
        try {
            // long userId=1;
            List<MissionQuestion> missionQuestions = missionQuestionService.findByMissionId(missionId);
            List<Question> li = new ArrayList<>();
            for (MissionQuestion u : missionQuestions)
                li.add(questionService.findOne(u.getQuestionId()));
            boolean start = false;
            boolean finish = true;
            if (li.size() > 0) start = true;
            for (Question u : li)
                if (!isPassed(u.getId(), request, response, session)) {
                    finish = false;
                    break;
                }
            long userId = ((User) session.getAttribute("user")).getId();
            UserThrough userThrough = userThroughService.findByUserIdAndMissionId(userId, missionId);
            if (userThrough == null) {
                userThrough = new UserThrough();
                userThrough.setUserId(userId);
                userThrough.setMissionId(missionId);
                userThrough.setStatus(UserThroughStatusEnum.NOT_THROUGH);
                userThroughService.saveUserThrough(userThrough);
            }
            //logger.info(userThrough.getStatus().toString());
            userThrough.setStatus(UserThroughStatusEnum.NOT_THROUGH);
            if (start) userThrough.setStatus(UserThroughStatusEnum.HAVE_IN_HAND);
            if (finish) userThrough.setStatus(UserThroughStatusEnum.COMPLETED);
            userThroughService.updateUserThrough(userThrough);
            return userThrough.getStatus().equals(UserThroughStatusEnum.COMPLETED);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
