package com.upic.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.upic.enums.LanguageTypeEnum;
import com.upic.enums.PublicTypeEnum;
import com.upic.enums.TheSubmitStatusEnum;
import com.upic.fastdfs.FastDFSClient;
import com.upic.po.*;
import com.upic.service.*;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.httpclient.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@RestController
@RequestMapping("/QuestionDetail")
public class QuestionDetailsController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private TheSubmitSerice theSubmitSerice;
    @Autowired
    private QuestionBankService questionBankService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private TeamMembersService teamMembersService;
    @Autowired
    private UserService userService;
    @Autowired
    private TestCaseService testCaseSerice;
    @Autowired
    private TheExampleService theExampleService;
    private static Logger logger = LoggerFactory.getLogger(UpdateQuestionController.class);

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
     * 判断权限
     *
     * @param questionId
     * @return
     */
    @GetMapping("isPermitted")
    @ApiOperation("判断权限")
    public Boolean isPermitted(long questionId, HttpServletRequest request,
                               HttpServletResponse response, HttpSession session) {
        try {
            Question question = questionService.findOne(questionId);
            PublicTypeEnum publicTypeEnum = question.getPublicType();
            if (publicTypeEnum.equals(PublicTypeEnum.PUBLIC))
                return Boolean.TRUE;
            else if (publicTypeEnum.equals(PublicTypeEnum.SEMI_PUBLIC)) {

                /* 判断是否为团队成员*/
                long id = question.getQuestionBankId();
                QuestionBank questionBank;
                questionBank = questionBankService.findOne(id);
                Team team = teamService.findOne(questionBank.getFollowId());
                long userId = Long.valueOf((String) request.getSession().getAttribute("user"));
                List<TeamMembers> teamMembers = null;
                teamMembers = teamMembersService.findByTeamId(team.getId());
                for (TeamMembers u : teamMembers)
                    if (u.getUserId() == userId) return Boolean.TRUE;
                return Boolean.FALSE;
            } else {
                /*判断是否为出题人*/
                long id = question.getQuestionBankId();
                QuestionBank questionBank;
                questionBank = questionBankService.findOne(id);
                long userId = Long.valueOf((String) request.getSession().getAttribute("user"));
                if (userId == questionBank.getFollowId()) return Boolean.TRUE;
                return Boolean.FALSE;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    class SortByName implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            return (((TestCase) o1).getTestCaseName().compareTo(((TestCase) o2).getTestCaseName()));
        }
    }

    /**
     * 获取样例
     *
     * @param questionId
     * @return
     */
    @GetMapping("getTheExamples")
    @ApiOperation("获取样例")
    public List<TheExample> getTheExamples(long questionId) {
        try {
            return theExampleService.findByQuestionId(questionId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String change(String fileName) throws IOException //从文件中读取数据
    {
        InputStream inputStream = FastDFSClient.downFile("group1", fileName);
        String result = CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));
        return result;
    }

    public static int sendCode(String time, String content, String in, String out) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        String smsUrl = "http://118.25.110.106:8081/pycheck";
        HttpPost httppost = new HttpPost(smsUrl);
        String strResult = "";
        String nin = in.replaceAll(" ", "\n");
        nin = nin.concat("\n");

        try {
            List<BasicNameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("time", time));
            params.add(new BasicNameValuePair("content", content));
            params.add(new BasicNameValuePair("in", nin));
            logger.info(nin);
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "utf-8");
            httppost.setEntity(entity);
            HttpResponse response = httpclient.execute(httppost);
            logger.info(httppost.toString());
            String conResult = EntityUtils.toString(response.getEntity());
            JSONObject sobj = new JSONObject();
            sobj = JSONObject.parseObject(conResult);
            String type = sobj.getString("type");
            String code = sobj.getString("code");
            String msg = sobj.getString("msg");
            logger.info(type);
            logger.info(code);
            logger.info(msg);
            msg = msg.replaceAll("\r|\n", "");
            out = out.replaceAll("\r|\n", "");
            if (code.equals("1001")) return 0;
            else if (msg.equals(out)) return 2;
            else return 1;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int check(String content, String in, long time, String out) throws IOException//测试，正确返回2，答案错误返回1，运行错误返回0
    {
        String ins;
        String outs;
        if (in.equals("")) ins = "";
        else ins = change(in);
        if (out.equals("")) outs = "";
        else outs = change(out);
        return sendCode(String.valueOf(time / 1000), content, ins, outs);
    }

    /**
     * 提交答案
     *
     * @param questionId
     * @param languageType
     * @param targetCode
     * @return
     */
    @GetMapping("submitCode")
    @ApiOperation("提交答案")
    public void submitCode(long questionId, LanguageTypeEnum languageType, String targetCode, HttpServletRequest request,
                           HttpServletResponse response, HttpSession session) {
        try {
            TheSubmit theSubmit = new TheSubmit();
            //theSubmit.setUserId(1);
            long userId = ((User) session.getAttribute("user")).getId();
            theSubmit.setUserId(userId);
            theSubmit.setQuestionId(questionId);
            theSubmit.setLanguageType(languageType);
            theSubmit.setCodeSize(targetCode.length());
            theSubmit.setTargetCode(targetCode);
            theSubmit.setStatus(TheSubmitStatusEnum.PENDING);
            theSubmit.setCreateTime(new Date());
            String code = theSubmit.getTargetCode();
            Question question = questionService.findOne(questionId);
            long time = question.getTimeLimit();
            List<TestCase> testCases = testCaseSerice.findByQuestionId(questionId);
            Collections.sort(testCases, new SortByName());
            List<String> ins = new ArrayList<>();
            List<String> outs = new ArrayList<>();
            if (testCases.size() == 1) {
                for (TestCase testCase : testCases)
                    outs.add(testCase.getRemoteFileName());
            } else {
                long flag = 1;
                for (TestCase testCase : testCases) {
                    if (flag == 1)
                        ins.add(testCase.getRemoteFileName());
                    else
                        outs.add(testCase.getRemoteFileName());
                    flag = 1 - flag;
                }
            }
            if (testCases.size() == 0)
                theSubmit.setStatus(TheSubmitStatusEnum.ACCEPTED);
            else if (testCases.size() == 1) {
                long l = check(theSubmit.getTargetCode(), "", question.getTimeLimit(), outs.get(0));
                if (l == 2)
                    theSubmit.setStatus(TheSubmitStatusEnum.ACCEPTED);
                else if (l == 1)
                    theSubmit.setStatus(TheSubmitStatusEnum.WRONG);
                else
                    theSubmit.setStatus(TheSubmitStatusEnum.ERROR);
            } else {
                boolean ac = true;
                for (int i = 0; i < ins.size(); i++) {
                    long l = check(theSubmit.getTargetCode(), ins.get(i), question.getTimeLimit(), outs.get(i));
                    if (l == 1) {
                        theSubmit.setStatus(TheSubmitStatusEnum.WRONG);
                        ac = false;
                        break;
                    } else if (l == 0) {
                        theSubmit.setStatus(TheSubmitStatusEnum.ERROR);
                        ac = false;
                        break;
                    }
                }
                if (ac) theSubmit.setStatus(TheSubmitStatusEnum.ACCEPTED);
            }

            theSubmitSerice.saveTheSubmit(theSubmit);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * 提交次数获取
     *
     * @param questionId
     * @return
     */
    @GetMapping("getSubmitTimes")
    @ApiOperation("提交次数获取")
    public Long getSubmitTimes(HttpServletRequest request,
                               HttpServletResponse response, HttpSession session, long questionId) {
        try {
            long userId = ((User) session.getAttribute("user")).getId();
            List<TheSubmit> theSubmits = theSubmitSerice.findByUserIdAndQuestionId(userId, questionId);
            return Long.valueOf(theSubmits.size());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取该题的提交记录
     *
     * @param questionId
     * @return
     */
    @GetMapping("getSubmit")
    @ApiOperation("获取该题的提交记录")
    public Page<TheSubmit> getSubmitTimes(HttpServletRequest request,
                                          HttpServletResponse response, HttpSession session, long questionId, Pageable pageable) {
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

    /**
     * 获取该题的通过比例
     *
     * @param questionId
     * @return
     */
    @GetMapping("getPercent")
    @ApiOperation("获取该题的通过率")
    public long getPercent(long questionId) {
        try {

            //logger.info(String.valueOf(indexController.getPassedNum(questionId)));
            long x = getSubmitNum(questionId);
            if (x == 0) return 0;
            return (long) (getPassedNum(questionId) * 100.0 / x);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }
}
