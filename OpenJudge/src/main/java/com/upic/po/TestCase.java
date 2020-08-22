package com.upic.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upic.base.entiy.BaseEntity;
import com.upic.enums.TestCaseTypeEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * 测试用例
 * Created by zhubuqing on 2018/3/29.
 */
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class TestCase extends BaseEntity {
    private String testCaseName; // 用例名称

    @Enumerated(EnumType.STRING)
    private TestCaseTypeEnum type; // 测试用例类型

    private String priority; // 优先级

    private String num; // 编号

    private Long questionId;//问题编号
    //private String unzipfileName;//测试文件名

    private String groupName;//组名

    private String remoteFileName;//远程文件名，用于下载

    private Integer timeLimit;//时间限制

    private Integer memoryLimit;//内存限制
}
