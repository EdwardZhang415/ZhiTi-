package com.upic.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.upic.enums.QuestionBankTypeEnum;
import com.upic.enums.TeamMembersTypeEnum;
import com.upic.enums.TheSubmitStatusEnum;
import com.upic.po.*;
import com.upic.service.*;
import com.upic.vo.NewTeamVo;
import com.upic.vo.QuestionBankVo;
import com.upic.vo.TeamMemberVo;
import com.upic.vo.TeamVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/Team")
public class TeamController {
    @Autowired
    private TeamService teamService;
    @Autowired
    private TeamMembersService teamMembersService;
    @Autowired
    private HomeworkService homeworkService;
    @Autowired
    private UserService userService;
    @Autowired
    private HomeworkQuestionService homeworkQuestionService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private TheSubmitSerice theSubmitSerice;
    @Autowired
    private QuestionBankService questionBankService;

    /**
     * 新建团队
     *
     * @return
     */
    @PostMapping("newTeam")
    @ApiOperation("新建团队")
    public String newTeam(NewTeamVo newTeamVo, HttpServletRequest request,
                          HttpServletResponse response, HttpSession session) {
        try {
            //if (request.getSession().getAttribute("user") ==null)
            //  return "对不起，请先登录";
            long userId = ((User) session.getAttribute("user")).getId();
            if (teamMembersService.findByUserId(userId) != null) return "Already Exist";
            Team team = new Team();
            BeanUtils.copyProperties(newTeamVo, team);
            team.setOpennessType(newTeamVo.getOpennessType());
            team.setUserId(userId);
            team.setCreateTime(new Date());
            teamService.saveTeam(team);
            TeamMembers teamMembers = new TeamMembers();
            teamMembers.setName("hnqw1214");
            teamMembers.setUserId(userId);
            //teamMembers.setName(((User) request.getSession().getAttribute("user")).getUsername());
            teamMembers.setTeamId(teamService.findByTeamName(team.getTeamName()).getId());
            teamMembers.setType(TeamMembersTypeEnum.CREATOR);
            teamMembersService.saveTeamMembers(teamMembers);
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    /**
     * 解散或退出团队
     *
     * @return
     */
    @PostMapping("deleteTeam")
    @ApiOperation("解散或退出团队")
    public String deleteTeam(HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        try {
            //if (request.getSession().getAttribute("user") ==null)
            //  return "对不起，请先登录";
            long userId = ((User) session.getAttribute("user")).getId();
            TeamMembers teamMembers = teamMembersService.findByUserId(userId);
            if (teamMembers == null) return "你还没有团队";
            teamMembersService.deleteTeamMembers(teamMembers.getId());

            if (teamMembers.getType().equals(TeamMembersTypeEnum.CREATOR)) {
                Team team = teamService.findByUserId(userId);
                teamService.deleteTeam(team.getId());
                List<TeamMembers> teamMembers1 = teamMembersService.findByTeamId(team.getId());
                for (TeamMembers teamMembers2 : teamMembers1)
                    teamMembersService.deleteTeamMembers(teamMembers2.getId());
            }
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    /**
     * 添加团队成员
     *
     * @param userName
     * @return
     */
    @PostMapping("addTeamMember")
    @ApiOperation("添加团队成员")
    public String addTeamMember(HttpServletRequest request,
                                HttpServletResponse response, HttpSession session, String userName) {
        try {
            // if (request.getSession().getAttribute("user") ==null)
            //   return "对不起，请先登录";
            long userId = ((User) session.getAttribute("user")).getId();
            TeamMembers teamMembers = teamMembersService.findByUserId(userId);
            if (teamMembers == null) return "你还没有团队";
            if (teamMembers.getType().equals(TeamMembersTypeEnum.ORDINARY))
                return "只有创建者和管理员才能添加团队成员";
            Team team = teamService.findByUserId(userId);
            User user = userService.findByUsername(userName);
            if (user == null) return "找不到该成员";
            if (teamMembersService.findByUserId(user.getId()) != null) return "该用户已有团队";
            teamMembers = new TeamMembers();
            teamMembers.setType(TeamMembersTypeEnum.ORDINARY);
            teamMembers.setTeamId(team.getId());
            teamMembers.setUserId(user.getId());
            teamMembers.setName(user.getUsername());
            teamMembersService.saveTeamMembers(teamMembers);
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    /**
     * 获取所有团队
     *
     * @param pageable
     * @return
     */
    @GetMapping("getAllTeam")
    @ApiOperation("获取所有团队")
    public Page<Team> getAllTeam(Pageable pageable) {
        try {
            return teamService.searchTeam(pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据用户名获取团队
     *
     * @return
     */
    @GetMapping("getTeamByUserId")
    @ApiOperation("根据用户名获取团队")
    public Team getTeamByUserId(HttpServletRequest request,
                                HttpServletResponse response, HttpSession session) {
        try {
            //if (request.getSession().getAttribute("user") ==null)
            //  return null;

            long userId = ((User) session.getAttribute("user")).getId();
            return teamService.findOne(teamMembersService.findByUserId(userId).getTeamId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取团队人数
     *
     * @return
     */
    @GetMapping("getNums")
    @ApiOperation("获取团队人数")
    public long getNums(HttpServletRequest request,
                        HttpServletResponse response, HttpSession session) {
        try {
            //if (request.getSession().getAttribute("user") ==null)
            //  return null;

            long userId = ((User) session.getAttribute("user")).getId();
            Team team = teamService.findOne(teamMembersService.findByUserId(userId).getTeamId());
            return teamMembersService.findByTeamId(team.getId()).size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取队长
     *
     * @return
     */
    @GetMapping("getCreator")
    @ApiOperation("获取队长")
    public String getCreator(HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        try {
            //if (request.getSession().getAttribute("user") ==null)
            //  return null;

            long userId = ((User) session.getAttribute("user")).getId();
            Team team = teamService.findOne(teamMembersService.findByUserId(userId).getTeamId());
            return teamMembersService.findByUserId(team.getUserId()).getName();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 设置团队
     *
     * @param teamVo
     * @return
     */
    @PostMapping("setTeam")
    @ApiOperation("设置团队")
    public String setTeam(TeamVo teamVo, HttpServletRequest request,
                          HttpServletResponse response, HttpSession session) {
        try {
            //if (request.getSession().getAttribute("user") ==null)
            //   return "对不起，请先登录";
            long userId = ((User) session.getAttribute("user")).getId();
            Team team = teamService.findOne(teamMembersService.findByUserId(userId).getTeamId());
            BeanUtils.copyProperties(teamVo, team);
            team.setOpennessType(teamVo.getOpennessType());
            teamService.updateTeam(team);
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 修改团队成员信息
     *
     * @param teamMemberVo
     * @return
     */
    @PostMapping("setTeamMember")
    @ApiOperation("修改团队成员信息")
    public String setTeamMember(TeamMemberVo teamMemberVo, HttpServletRequest request,
                                HttpServletResponse response, HttpSession session) {
        try {
            long userId = ((User) session.getAttribute("user")).getId();
            TeamMembers teamMembers = teamMembersService.findByUserId(userId);
            teamMembers.setName(teamMemberVo.getName());
            //teamMembers.setType(teamMemberVo.getType());
            teamMembersService.updateTeamMembers(teamMembers);
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询是否已完成作业
     *
     * @param userId
     * @param homeworkId
     * @return
     */
    private boolean isFinished(long userId, long homeworkId) {
        List<HomeworkQuestion> homeworkQuestions = homeworkQuestionService.findByHomeworkId(homeworkId);
        List<Question> questions = new ArrayList<>();
        for (HomeworkQuestion homeworkQuestion : homeworkQuestions)
            questions.add(questionService.findOne(homeworkQuestion.getQuestionId()));
        for (Question question : questions) {
            List<TheSubmit> theSubmits = theSubmitSerice.findByUserIdAndQuestionId(userId, question.getId());
            if (theSubmits.stream().filter(theSubmit -> theSubmit.getStatus().equals(TheSubmitStatusEnum.ACCEPTED)).count() == 0)
                return false;
        }
        return true;
    }

    /**
     * 查看用户待完成作业
     *
     * @return
     */
    @GetMapping("getUnfinishedHomework")
    @ApiOperation("查看用户待完成作业")
    public Page<Homework> getUnfinishedHomework(HttpServletRequest request,
                                                HttpServletResponse response, HttpSession session, Pageable pageable) {
        try {
            //if (request.getSession().getAttribute("user") ==null)
            //  return null;
            long userId = ((User) session.getAttribute("user")).getId();
            Team team = teamService.findOne(teamMembersService.findByUserId(userId).getTeamId());
            List<Homework> homeworks = homeworkService.findByTeamId(team.getId());
            List<Homework> unfinishedHomeworks = new ArrayList<>();
            for (Homework homework : homeworks)
                if (!isFinished(userId, homework.getId()))
                    unfinishedHomeworks.add(homework);
            return new PageImpl<>(unfinishedHomeworks, pageable, unfinishedHomeworks.size());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取团队作业
     *
     * @return
     */
    @GetMapping("getHomeworks")
    @ApiOperation("获取团队作业")
    public Page<Homework> getHomeworks(HttpServletRequest request,
                                       HttpServletResponse response, HttpSession session, Pageable pageable) {
        try {
            //if (request.getSession().getAttribute("user") ==null)
            // return null;
            long userId = ((User) session.getAttribute("user")).getId();
            Team team = teamService.findOne(teamMembersService.findByUserId(userId).getTeamId());
            return homeworkService.findByTeamId(team.getId(), pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取作业详情
     *
     * @param homeworkId
     * @return
     */
    @GetMapping("getHomework")
    @ApiOperation("获取作业详情")
    public Homework getHomework(long homeworkId) {
        try {
            return homeworkService.findOne(homeworkId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据作业获取题目
     *
     * @param homeworkId
     * @param pageable
     * @return
     */
    @GetMapping("getHomeworkQuestions")
    @ApiOperation("根据作业获取题目")
    public Page<Question> getHomeworkQuestions(long homeworkId, Pageable pageable) {
        try {
            List<HomeworkQuestion> homeworkQuestions = homeworkQuestionService.findByHomeworkId(homeworkId);
            List<Question> questions = new ArrayList<>();
            for (HomeworkQuestion homeworkQuestion : homeworkQuestions)
                questions.add(questionService.findOne(homeworkQuestion.getQuestionId()));
            return new PageImpl<>(questions, pageable, questions.size());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 新建作业
     *
     * @param homeworkName
     * @param homeworkType
     * @return
     */
    @PostMapping("setHomeWork")
    @ApiOperation("新建作业")
    public String setHomework(String homeworkName, String homeworkType, @RequestParam(value = "theQuestions[]", required = false) long theQuestionIds[], HttpServletRequest request,
                              HttpServletResponse response, HttpSession session) {
        try {
            // if (request.getSession().getAttribute("user") ==null)
            // return "对不起，请先登录";
            long userId = ((User) session.getAttribute("user")).getId();
            if (teamMembersService.findByUserId(userId).getType().equals(TeamMembersTypeEnum.ORDINARY))
                return "只有创建者或管理员才能新建作业";
            Homework homework = new Homework();
            homework.setHomeworkName(homeworkName);
            homework.setType(homeworkType);
            homework.setTeamId(teamMembersService.findByUserId(userId).getTeamId());
            homeworkService.saveHomework(homework);
            homework = homeworkService.findByHomeworkName(homeworkName);
            if (theQuestionIds == null || theQuestionIds.length == 0) return "请添加题目";
            for (long theQuestionId : theQuestionIds) {
                HomeworkQuestion homeworkQuestion = new HomeworkQuestion();
                homeworkQuestion.setHomeworkId(homework.getId());
                homeworkQuestion.setQuestionId(theQuestionId);
                homeworkQuestionService.saveHomeworkQuestion(homeworkQuestion);
            }
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isPermitted(long userId, long questionBankId) {
        QuestionBank questionBank = questionBankService.findOne(userId);
        if (questionBank.getType().equals(QuestionBankTypeEnum.PUBLIC)) return true;
        if (questionBank.getType().equals(QuestionBankTypeEnum.PRIVATE)) return userId == questionBank.getFollowId();
        Team team = teamService.findOne(questionBank.getFollowId());
        List<TeamMembers> teamMembers = teamMembersService.findByTeamId(team.getId());
        return teamMembers.stream().anyMatch(teamMembers1 -> teamMembers1.getUserId() == userId);
    }

    /**
     * 获取题库
     *
     * @return
     * @pageable
     */
    @GetMapping("getQuestionBanks")
    @ApiOperation("获取题库")
    public Page<QuestionBank> getQuestionBanks(HttpServletRequest request,
                                               HttpServletResponse response, HttpSession session, Pageable pageable) {
        try {
            // if (request.getSession().getAttribute("user") ==null)
            //  return null;
            long userId = ((User) session.getAttribute("user")).getId();
            Team team = teamService.findByUserId(userId);
            List<QuestionBank> questionBanks = questionBankService.searchQuestionBank().stream().filter(questionBank -> isPermitted(userId, questionBank.getId())).collect(Collectors.toList());
            return new PageImpl<>(questionBanks, pageable, questionBanks.size());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 新建题库
     *
     * @return
     */
    @PostMapping("newQuestionBank")
    @ApiOperation("新建题库")
    public String newQuestionBank(QuestionBankVo questionBankVo, HttpServletRequest request,
                                  HttpServletResponse response, HttpSession session) {
        try {
            // if (request.getSession().getAttribute("user") ==null)
            //  return "请先登录";
            long userId = ((User) session.getAttribute("user")).getId();
            QuestionBank questionBank = new QuestionBank();
            BeanUtils.copyProperties(questionBank, questionBankVo);
            if (questionBank.getType().equals(QuestionBankTypeEnum.PRIVATE))
                questionBank.setFollowId(userId);
            if (questionBank.getType().equals(QuestionBankTypeEnum.TEAM))
                questionBank.setFollowId(teamMembersService.findByUserId(userId).getTeamId());
            questionBankService.saveQuestionBank(questionBank);
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

}
