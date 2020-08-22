package com.upic.controller;

import com.aliyuncs.exceptions.ClientException;
import com.upic.enums.TheSubmitStatusEnum;
import com.upic.po.TheSubmit;
import com.upic.po.User;
import com.upic.service.TheSubmitSerice;
import com.upic.service.UserService;
import com.upic.utils.CheckUtil;
import com.upic.utils.CommonUtils;
import com.upic.utils.MyMD5Util;
import com.upic.utils.SmsUtil;
import com.upic.vo.PassedNumVo;
import com.upic.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;

/**
 * Created by song on 2018/5/21.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private static final String KEY = "abc123"; // KEY为自定义秘钥
    @Autowired
    private TheSubmitSerice theSubmitSerice;

    private static Logger logger = LoggerFactory.getLogger(UpdateQuestionController.class);

    @PostMapping("/login")
    public Result login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpSession httpSession) {
        if (email == null || password == null) {
            return new Result(1, "用户名或密码错误", null);
        }
        User user = userService.findByEmail(email);
        if (user == null) {
            return new Result(1, "用户名或密码错误", null);
        }
        String db = user.getPassword();
        boolean result = MyMD5Util.isRight(password, db);
        if (result) {
            httpSession.setAttribute("user", user);
            return new Result(0, "登录成功", user);
        }
        return new Result(1, "用户名或密码错误", null);

    }


    /**
     * var obj = {
     * phoneNumber: phoneNumber
     * };
     *
     * @param requestMap
     * @return {
     * "hash":hash
     * "tamp":currentTime
     * }
     */
    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result sendMsg(@RequestBody Map<String, Object> requestMap) {
        String phoneNumber = requestMap.get("phoneNumber").toString();
        String randomNum = CommonUtils.createRandomNum(6);// 生成随机数
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 5);
        String currentTime = sf.format(c.getTime());// 生成5分钟后时间，用户校验是否过期
        try {
            SmsUtil.sendSms(phoneNumber, randomNum); //此处执行发送短信验证码方法
        } catch (ClientException e) {
            e.printStackTrace();
        }
        // String hash =  MD5Utils.getMD5Code(KEY + "@" + currentTime + "@" + randomNum);//生成MD5值
        String hash = MyMD5Util.md5(KEY + "@" + currentTime + "@" + randomNum);//生成MD5值
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("hash", hash);
        resultMap.put("tamp", currentTime);
        return new Result(0, "成功", resultMap); //将hash值和tamp时间返回给前端
    }


    /**
     * var data = {
     * msgNum: inputMsgNum,
     * tamp: messageData.tamp,
     * hash: messageData.hash,
     * email: email,
     * password: password
     * };
     *
     * @param requestMap
     * @return
     */
    @RequestMapping(value = "/validateNum", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result validateNum(@RequestBody Map<String, Object> requestMap) {
        String requestHash = requestMap.get("hash").toString();
        String tamp = requestMap.get("tamp").toString();
        String msgNum = requestMap.get("msgNum").toString();
        String phone = requestMap.get("phone").toString();
        //String hash = MD5Utils.getMD5Code(KEY + "@" + tamp + "@" + msgNum);
        String hash = MyMD5Util.md5(KEY + "@" + tamp + "@" + msgNum);

        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c = Calendar.getInstance();
        String currentTime = sf.format(c.getTime());
        if (tamp.compareTo(currentTime) > 0) {
            if (hash.equalsIgnoreCase(requestHash)) {
//校验成功
                // System.out.println("校验成功");
                String email = requestMap.get("email").toString();
                boolean check = CheckUtil.checkEmail(email);
                if (!check) {
                    return new Result(5, "邮箱格式不正确，请更换邮箱", null);
                }
                String password = requestMap.get("password").toString();
                User byEmail = userService.findByEmail(email);
                if (byEmail != null) {
                    return new Result(3, "邮箱已注册，请更换邮箱", null);
                }
                if (password == null || password.length() < 6 || password.length() > 16) {
                    return new Result(4, "密码格式不正确，应为6-16位", null);
                }
                User user = new User();
                user.setEmail(email);
                user.setPassword(MyMD5Util.getDbPass(password));
                user.setPhone(phone);
                //user.setVersion(1);//数据库非空要求
                //user.setIsBlog(1);//数据库非空要求
                userService.saveUser(user);
                return new Result(0, "注册成功", user);

            } else {
//验证码不正确，校验失败
                //  System.out.println("验证码不正确，校验失败");
                return new Result(1, "验证码不正确，校验失败", null);
            }
        } else {
// 超时
            //  System.out.println("超时");
            return new Result(2, "超时", null);
        }

    }

    @PostMapping("setPersonalitySignature")
    @ApiOperation("修改个性签名")
    public String setPersonalitySignature(HttpSession httpSession, String personalitySignature) {
        try {
            User user = (User) httpSession.getAttribute("user");
            user.setPersonalitySignature(personalitySignature);
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    @GetMapping("getHistoryPercent")
    @ApiOperation("获取累计通过率")
    public List<PassedNumVo> getHistoryPercent(HttpSession httpSession) {
        try {
            User user = (User) httpSession.getAttribute("user");
            List<TheSubmit> theSubmits = theSubmitSerice.findByUserId(user.getId());
            Calendar cal = Calendar.getInstance();
            Date date = new Date();
            cal.setTime(date);
            long month = cal.get(Calendar.MONTH);
            month++;
            long year = cal.get(Calendar.YEAR);
            List<PassedNumVo> passedNumVos = new ArrayList<>();
            for (int i = 1; i <= 6; i++) {
                PassedNumVo passedNumVo = new PassedNumVo();
                passedNumVo.setMonth(month);
                long passed = 0, total = 0;
                for (TheSubmit theSubmit : theSubmits) {
                    //logger.info(theSubmit.getCreateTime().toString());
                    date = theSubmit.getCreateTime();
                    cal.setTime(date);
                    long theMonth = cal.get(Calendar.MONTH);
                    theMonth++;
                    long theYear = cal.get(Calendar.YEAR);
                    if (theMonth == month && theYear == year) {
                        total++;
                        if (theSubmit.getStatus().equals(TheSubmitStatusEnum.ACCEPTED))
                            passed++;
                    }
                }
                if (total == 0)
                    passedNumVo.setPercent(0l);
                else
                    passedNumVo.setPercent((long) (passed * 1.0 / total * 100));
                passedNumVos.add(passedNumVo);
                month--;
                if (month == 0) {
                    month = 12;
                    year--;
                }
            }
            return passedNumVos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("getUserSubmit")
    @ApiOperation("获取个人提交记录")
    public Page<TheSubmit> getUserSubmit(Pageable pageable, HttpSession session) {
        try {
            long userId = ((User) session.getAttribute("user")).getId();
            return theSubmitSerice.findByUserId(userId, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
//    @RequestMapping("/test")
//    public Result test(){
//        Map<String, Object> resultMap = new HashMap<>();
//        resultMap.put("hash", 1);
//        resultMap.put("tamp", 1);
//        return new Result(0,"success",resultMap);
//    }
}
