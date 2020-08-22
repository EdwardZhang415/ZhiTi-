package com.upic.base.entiy;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 6998031580391808779L;
    @Id
    @GeneratedValue
    private Long id;

    @Version
    private int version;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    private String field[]=new String[5];
}
