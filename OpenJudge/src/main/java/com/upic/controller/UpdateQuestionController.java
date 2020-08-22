package com.upic.controller;

import com.upic.enums.PublicTypeEnum;
import com.upic.fastdfs.FastDFSClient;
import com.upic.fastdfs.FastDFSFile;
import com.upic.po.*;
import com.upic.service.QuestionLabelService;
import com.upic.service.QuestionService;
import com.upic.service.TestCaseService;
import com.upic.service.TheExampleService;
import com.upic.utils.ZipUtil;
import com.upic.vo.QuestionVo;
import com.upic.vo.TheExampleParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/UpdateQuestion")
public class UpdateQuestionController {

    private static Logger logger = LoggerFactory.getLogger(UpdateQuestionController.class);

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TestCaseService testCaseService;
    @Autowired
    private TheExampleService theExampleService;
    @Autowired
    private QuestionLabelService questionLabelService;

    @GetMapping("/getAllTestcase")
    @ApiOperation("获取所有测试数据")
    public List<TestCase> getAllTestcase(@RequestParam("questionId") Long questionId) {
        List<TestCase> all = testCaseService.findByQuestionId(questionId);
        return all;
    }

    @PostMapping("updateLimit")
    @ApiOperation("更新测试点内存和时间限制")
    public String updateLimit(@RequestBody TestCase[] list) {
        for (TestCase testCase : list) {
            Long id = testCase.getId();
            TestCase one = testCaseService.findOne(id);
            one.setTimeLimit(testCase.getTimeLimit());
            one.setMemoryLimit(testCase.getMemoryLimit());
            testCaseService.saveTestCase(one);
        }
        return "success";
    }

    @PostMapping("updatePublicType")
    @ApiOperation("更新公开类型")
    public String updatePublicType(@RequestParam("questionId") Long questionId,
                                   @RequestParam("code") int code) {
        Question one = questionService.findOne(questionId);
        one.setPublicType(PublicTypeEnum.getEnum(code));
        questionService.saveQuestion(one);
        return "success";
    }

    @PostMapping("/uploadQuestion")
    @ApiOperation("上传题目")
    public Long uploadQuestion(@RequestBody QuestionVo questionVo) {
        try {
            Question question = new Question();
            BeanUtils.copyProperties(questionVo, question);
            question.setPublicType(PublicTypeEnum.getEnum(questionVo.getPublicTypeNum()));
            Question question1 = questionService.saveQuestion(question);
            Long questionId = question1.getId();
            List<TheExampleParam> theExamples = questionVo.getTheExamples();
            for (TheExampleParam theExampleParam : theExamples) {
                TheExample theExample = new TheExample();
                theExample.setExampleInput(theExampleParam.getExampleInput());
                theExample.setExampleOutput(theExampleParam.getExampleOutput());
                theExample.setQuestionId(questionId);
                theExampleService.saveTheExample(theExample);
            }
            List<Long> labels = questionVo.getLabels();
            for (Long labelId : labels) {
                QuestionLabel questionLabel = new QuestionLabel();
                questionLabel.setQuestionId(questionId);
                questionLabel.setTheLabelId(labelId);
                questionLabelService.saveQuestionLabel(questionLabel);
            }

            return questionId;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }

    }

    @PostMapping("/upload") //new annotation since 4.3
    @ApiOperation("上传单个测试点")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("questionId") Long questionId) {
        if (file.isEmpty()) {
            return "FILE IS Empty!";
            // redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            // return "redirect:result";
        }

        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!ext.equals("zip")) {
            return "File name is wrong!";
            // redirectAttributes.addFlashAttribute("message", "必须为zip文件");
            // return "redirect:result";
        }

        try {
            InputStream in = file.getInputStream();
            File zipFile = new File("/oj/" + fileName);
            FileOutputStream out = new FileOutputStream(zipFile);
            FileCopyUtils.copy(in, out);
            String zipFilePath = "/oj/" + fileName;
            // logger.info(zipFilePath);
            String unzipFilePath = "/oj/unzip";
            //logger.info(unzipFilePath);
            ZipUtil.unzip(zipFilePath, unzipFilePath, false);
            File unzipFileDir = new File(unzipFilePath);
            File[] files = unzipFileDir.listFiles();
            Map<String, String> inMap = new HashMap<>();
            Map<String, String> outMap = new HashMap<>();
            for (File file1 : files) {
                String unzipfileName = file1.getName();//test001.in
                String unzipfileNameWithoutExt = unzipfileName.substring(0, unzipfileName.lastIndexOf("."));
                String extName = unzipfileName.substring(unzipfileName.lastIndexOf(".") + 1);
                if (extName.equals("in")) {
                    inMap.put(unzipfileNameWithoutExt, unzipfileName);
                } else if (extName.equals("out")) {
                    outMap.put(unzipfileNameWithoutExt, unzipfileName);
                } else {
                    return "文件命名不正确";
                }
            }
            if (inMap.size() != outMap.size()) {
                return "格式错误";
            }
            Set<Map.Entry<String, String>> entries = inMap.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                String key = entry.getKey();
                if (outMap.get(key) == null) {
                    return "格式错误";
                }
            }
            for (File file1 : files) {
                String unzipfileName = file1.getName();//test001
                logger.info("unzipfileName" + unzipfileName);
                String[] fileAbsolutePath = saveFile(file1);
                String group_name = fileAbsolutePath[0];
                String remoteFileName = fileAbsolutePath[1];
                //logger.info("path"+path);
                //unzipfileName group_name，remoteFileName保存数据库
                TestCase testCase = new TestCase();
                testCase.setTestCaseName(unzipfileName);
                testCase.setGroupName(group_name);
                testCase.setRemoteFileName(remoteFileName);
                testCase.setQuestionId(questionId);
                testCaseService.saveTestCase(testCase);

            }
            //删除本地文件
            deleteDir(unzipFileDir);
            boolean delete = zipFile.delete();
            logger.info("delete:   " + delete + "filename   " + fileName);


            //            // Get the file and save it somewhere
            //           // String path=saveFile(file);
            //           // redirectAttributes.addFlashAttribute("message",
            //                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
            //           // redirectAttributes.addFlashAttribute("path",
            //           //         "file path url '" + path + "'");
            return "success";
        } catch (Exception e) {
            logger.error("upload file failed", e);
            return null;
        }

    }

    //    @GetMapping("/result")
    //    public String uploadStatus() {
    //        return "result";
    //    }

    public String[] saveFile(File file) throws IOException {
        String[] fileAbsolutePath = {};
        String fileName = file.getName();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        byte[] file_buff = null;
        InputStream inputStream = new FileInputStream(file);
        if (inputStream != null) {
            int len1 = inputStream.available();
            file_buff = new byte[len1];
            inputStream.read(file_buff);
        }
        inputStream.close();
        FastDFSFile fastFile = new FastDFSFile(fileName, file_buff, ext);
        try {
            fileAbsolutePath = FastDFSClient.upload(fastFile);  //upload to fastdfs
        } catch (Exception e) {
            logger.error("upload file Exception!", e);
        }
        if (fileAbsolutePath == null) {
            logger.error("upload file failed,please upload again!");
        }
        String path = FastDFSClient.getTrackerUrl() + fileAbsolutePath[0] + "/" + fileAbsolutePath[1];
        logger.info("path" + path);
        // return path;
        return fileAbsolutePath;
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
