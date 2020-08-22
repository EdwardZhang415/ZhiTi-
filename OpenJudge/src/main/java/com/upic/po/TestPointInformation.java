package com.upic.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upic.base.entiy.BaseEntity;
import com.upic.enums.CompileStatusEnum;
import com.upic.enums.TestPointInformationStatusEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * 测试点信息
 * Created by zhubuqing on 2018/3/29.
 */
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class TestPointInformation extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private TestPointInformationStatusEnum status; // 测试点状态

    private String compileInformation; // 编译信息

    @Enumerated(EnumType.STRING)
    private CompileStatusEnum compileStatus; // 编译状态

    private double timeConsuming; // 消耗时间

    private double memoryOccupancy; // 内存占用
}
