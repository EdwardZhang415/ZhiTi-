package com.upic.controller;

import com.upic.enums.QuestionStatusEnum;
import com.upic.enums.TheSubmitStatusEnum;
import com.upic.po.*;
import com.upic.service.*;
import com.upic.vo.TheLabelVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.awt.*;

@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private QuestionBankService questionBankService;
    @Autowired
    private QuestionLabelService questionLabelService;
    @Autowired
    private TheLabelService theLabelService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private TheSubmitSerice theSubmitSerice;

    /**
     * 获取题库
     *
     * @param pageable
     * @return
     */
    @GetMapping("/getList")
    @ApiOperation("获取题库")
    public Page<QuestionBank> getList(Pageable pageable) {
        try {
            return questionBankService.searchQuestionBank(pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取所有标签
     *
     * @param pageable
     * @return
     */
    @GetMapping("/getAllLabel")
    @ApiOperation("获取所有标签")
    public Page<TheLabel> getAllLabel(Pageable pageable) {
        try {
            return theLabelService.searchTheLabel(pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取标签
     *
     * @param questionId
     * @return
     */
    @GetMapping("/getLable")
    @ApiOperation("获取标签")
    public List<TheLabel> getLable(long questionId) {
        try {
            List<QuestionLabel> questionBanks = questionLabelService.findByQuestionId(questionId);
            List<TheLabel> theLabels = new ArrayList<>();
            for (QuestionLabel questionLabel : questionBanks)
                theLabels.add(theLabelService.findOne(questionLabel.getTheLabelId()));
            return theLabels;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取所有题目
     *
     * @param pageable
     * @return
     */
    @GetMapping("getAllQuestion")
    @ApiOperation("根据ID获取题目")
    public Page<Question> getAllQuestion(Pageable pageable) {
        try {
            return questionService.searchQuestion(pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 根据ID获取题目
     *
     * @param questionId
     * @return
     */
    @GetMapping("getQuestionById")
    @ApiOperation("根据ID获取题目")
    public Question getQuestionById(long questionId) {
        try {
            return questionService.findOne(questionId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据状态获取题目
     *
     * @param status
     * @param pageable
     * @return
     */
    @GetMapping("getQuestionsByStatus")
    @ApiOperation("根据状态获取题目")
    public Page<Question> getQuestionsByStatus(QuestionStatusEnum status, Pageable pageable) {
        try {
            return questionService.findByStatus(status, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 搜索题目名获取题目
     *
     * @param name
     * @param pageable
     * @return
     */
    @GetMapping("getQuestionsByName")
    @ApiOperation("搜索题目名获取题目")
    public Page<Question> getQuestionsByName(String name, String degree, Pageable pageable) {
        try {
            if (name == null) name = "";
            if (degree.equals("0"))
                return questionService.findByQuestionNameLike(name, pageable);
            else
                return questionService.findByQuestionNameLikeAndDegreeOfDifficulty(name, degree, pageable);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据标签获取题目
     *
     * @param theLabels
     * @param pageable
     * @return
     */
    @GetMapping("getQuestionsByTags")
    @ApiOperation("根据标签获取题目")
    public Page<Question> getQuestionsByTags(@RequestParam(value = "theLabels[]") String[] theLabels, Pageable pageable) {
        try {

            List<Long> theLabelIds = new ArrayList<>();
            for (String u : theLabels)
                theLabelIds.add(theLabelService.findByTheLabelName(u).getId());

            List<QuestionLabel> questionLabels;
            questionLabels = questionLabelService.findByTheLabelIdIn(theLabelIds);
            List<Question> result = new ArrayList<>();
            for (QuestionLabel u : questionLabels)
                if (!result.contains(questionService.findOne(u.getQuestionId())))
                    result.add(questionService.findOne(u.getQuestionId()));
            return new PageImpl<>(result, pageable, result.size());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据题目名、标签及难度获取题目
     *
     * @param theLabels
     * @param name
     * @param degree
     * @param pageable
     * @return
     */
    @GetMapping("getQuestionsByTagsAndName")
    @ApiOperation("根据题目名、标签及难度获取题目")
    public Page<Question> getQuestionsByTagsAndName(@RequestParam(value = "theLabels[]", required = false) long[] theLabels, String name, String degree, Pageable pageable) {
        try {
            if (theLabels == null)
                return getQuestionsByName(name, degree, pageable);
            List<Long> theLabelIds = new ArrayList<>();
            for (long u : theLabels)
                theLabelIds.add(theLabelService.findOne(u).getId());
            List<QuestionLabel> questionLabels;
            questionLabels = questionLabelService.findByTheLabelIdIn(theLabelIds);
            List<Question> result = new ArrayList<>();
            if (name == null) name = "";
            for (QuestionLabel u : questionLabels) {
                if (questionService.findOne(u.getQuestionId()).getQuestionName().contains(name))
                    if (!result.contains(questionService.findOne(u.getQuestionId()))) {
                        Question q = questionService.findOne(u.getQuestionId());
                        if (q.getDegreeOfDifficulty().equals(degree) || degree.equals("0"))
                            result.add(q);
                    }
            }
            return new PageImpl<>(result, pageable, result.size());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询通过人数
     *
     * @param questionId
     * @return
     */
    @GetMapping("getPassedNum")
    @ApiOperation("查询通过人数")
    public Long getPassedNum(long questionId) {
        try {
            List<TheSubmit> theSubmits = theSubmitSerice.findByQuestionId(questionId);
            if (theSubmits == null) return 0l;
            return theSubmits.stream().filter(theSubmit -> theSubmit.getStatus().equals(TheSubmitStatusEnum.ACCEPTED)).count();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询提交人数
     *
     * @param questionId
     * @return
     */
    @GetMapping("getSubmitNum")
    @ApiOperation("查询提交人数")
    public Long getSubmitNum(long questionId) {
        try {
            List<TheSubmit> theSubmits = theSubmitSerice.findByQuestionId(questionId);
            return Long.valueOf(theSubmits.size());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
