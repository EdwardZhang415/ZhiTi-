package com.upic.controller;

import com.upic.enums.TeamMembersTypeEnum;
import com.upic.enums.TheSubmitStatusEnum;
import com.upic.po.*;
import com.upic.service.*;
import com.upic.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by song on 2018/5/24.
 */

@RestController
@RequestMapping("/homework")
public class HomeworkController {

    @Autowired
    private TeamMembersService teamMembersService;

    @Autowired
    private HomeworkService homeworkService;

    @Autowired
    private HomeworkQuestionService homeworkQuestionService;

    @Autowired
    private TheSubmitSerice theSubmitSerice;

    @Autowired
    private UserService userService;

    private static Logger logger = LoggerFactory.getLogger(UpdateQuestionController.class);

    /**
     * 查询用户是否完成所有作业
     */
    @GetMapping("/isDoneAllHomework")
    @ApiOperation("查询用户是否完成所有作业")
    public Result isDoneAllHomework(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        TeamMembers teamMembers = teamMembersService.findByUserId(user.getId());
        long teamId = teamMembers.getTeamId();
        List<Homework> all = homeworkService.findByTeamId(teamId);
        if (CollectionUtils.isEmpty(all)) {
            return new Result(0, "成功", true);
        }
        for (Homework homework : all) {
            Long homeworkId = homework.getId();
            List<HomeworkQuestion> homeworkQuestions = homeworkQuestionService.findByHomeworkId(homeworkId);
            if (CollectionUtils.isEmpty(homeworkQuestions)) {
                return new Result(0, "成功", true);
            }
            for (HomeworkQuestion homeworkQuestion : homeworkQuestions) {
                long questionId = homeworkQuestion.getQuestionId();
                List<TheSubmit> allSubmmit = theSubmitSerice.findByUserIdAndQuestionId(user.getId(), questionId);
                if (CollectionUtils.isEmpty(allSubmmit)) {
                    return new Result(0, "成功", false);
                }
                boolean flag = false;
                for (TheSubmit theSubmit : allSubmmit) {
                    if (theSubmit.getStatus().equals(TheSubmitStatusEnum.ACCEPTED)) {
                        flag = true;
                        break;
                    }
                }
                if (flag == false) {
                    return new Result(0, "成功", flag);
                }

            }


        }


        return new Result(0, "成功", true);
    }

    /**
     * 查看团队中完成作业人数
     */
    @GetMapping("/getDoneNumber")
    @ApiOperation("查看团队中完成作业人数")
    public Result getDoneNumber(@RequestParam("homeworkId") long homeworkId,
                                HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        TeamMembers teamMembers = teamMembersService.findByUserId(user.getId());
        long teamId = teamMembers.getTeamId();
        List<TeamMembers> all = teamMembersService.findByTeamId(teamId);
        if (CollectionUtils.isEmpty(all)) {
            return new Result(0, "成功", 0);
        }
        List<HomeworkQuestion> allHomeworkQuestion = homeworkQuestionService.findByHomeworkId(homeworkId);
        int number = 0;
        for (TeamMembers teamMembers1 : all) {
            long userId = teamMembers1.getUserId();
            boolean add = true;
            if (CollectionUtils.isEmpty(allHomeworkQuestion)) {
                return new Result(0, "成功", 0);
            }
            for (HomeworkQuestion homeworkQuestion : allHomeworkQuestion) {
                List<TheSubmit> allTheSubmit = theSubmitSerice.findByUserIdAndQuestionId(userId, homeworkQuestion.getQuestionId());
                boolean flag = false;
                if (CollectionUtils.isEmpty(allTheSubmit)) {
                    return new Result(0, "成功", 0);
                }
                for (TheSubmit theSubmit : allTheSubmit) {
                    if (theSubmit.getStatus().equals(TheSubmitStatusEnum.ACCEPTED)) {
                        flag = true;
                        break;
                    }
                }
                if (flag == false) {
                    add = false;
                }
            }
            if (add == true) {
                number++;
            }

        }
        return new Result(0, "成功", number);
    }

    /**
     * 获取排行榜
     */
    @GetMapping("getRank")
    @ApiOperation("获取排行榜")
    public Result getRank(@RequestParam(value = "page", defaultValue = "0") Integer page,
                          @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "acNumber");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<User> users = userService.searchUser(pageable);
        return new Result(0, "成功", users);
    }


    /**
     * 获取团队管理员
     * 团队id和type
     */
    @GetMapping("/getAdmin")
    @ApiOperation("获取团队管理员")
    public Result getAdmin(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        TeamMembers teamMembers = teamMembersService.findByUserId(user.getId());
        List<TeamMembers> list = teamMembersService.findByTeamIdAndType(teamMembers.getTeamId(), TeamMembersTypeEnum.ADMINISTRATOR);
        return new Result(0, "成功", list);
    }


    /**
     * 获取团队中所有普通成员
     * 团队id和type
     */
    @GetMapping("/getOrdinary")
    @ApiOperation("获取团队中所有普通成员")
    public Result getOrdinary(HttpSession httpSession, HttpServletRequest request) {
        User user = (User) httpSession.getAttribute("user");
        logger.info(request.getContextPath());
        TeamMembers teamMembers = teamMembersService.findByUserId(user.getId());
        List<TeamMembers> list = teamMembersService.findByTeamIdAndType(teamMembers.getTeamId(), TeamMembersTypeEnum.ORDINARY);
        return new Result(0, "成功", list);
    }

    /**
     * 设置团队管理员
     * 用户id
     */
    @PostMapping("/setAdmin")
    @ApiOperation("设置团队管理员")
    public Result setAdmin(@RequestBody Long[] ids, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        TeamMembers teamMembers = teamMembersService.findByUserId(user.getId());
        if (!teamMembers.getType().equals(TeamMembersTypeEnum.CREATOR)) {
            return new Result(1, "您不是创建者，无法设置管理员", null);
        }
        for (Long id : ids) {
            TeamMembers one = teamMembersService.findByUserId(id);
            one.setType(TeamMembersTypeEnum.ADMINISTRATOR);
            teamMembersService.updateTeamMembers(one);
        }
        return new Result(0, "成功", null);
    }

    /**
     * 取消团队管理员
     * 用户id
     */
    @PostMapping("/deleteAdmin")
    @ApiOperation("取消团队管理员")
    public Result deleteAdmin(@RequestBody Long[] ids, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        TeamMembers teamMembers = teamMembersService.findByUserId(user.getId());
        if (!teamMembers.getType().equals(TeamMembersTypeEnum.CREATOR)) {
            return new Result(1, "您不是创建者，无法设置管理员", null);
        }
        for (Long id : ids) {
            TeamMembers one = teamMembersService.findByUserId(id);
            one.setType(TeamMembersTypeEnum.ORDINARY);
            teamMembersService.updateTeamMembers(one);
        }
        return new Result(0, "成功", null);
    }

    /**
     * 获取个人提交记录
     * 用户ID
     */
    @GetMapping("/getSubmit")
    @ApiOperation("获取个人提交记录")
    public Result getSubmit(@RequestParam(value = "page", defaultValue = "0") Integer page,
                            @RequestParam(value = "size", defaultValue = "10") Integer size,
                            HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<TheSubmit> submit = theSubmitSerice.findByUserId(user.getId(), pageable);
        return new Result(0, "成功", submit);

    }
}
