package com.qin.entity;


import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * <p>
 * 
 * </p>
 *
 * @author qin
 * @since 2020-11-17
 */
@Data
public class MBlog {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Integer Id;

    private Integer userId;

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "摘要不能为空")
    private String description;

    @NotBlank(message = "内容不能空")
        private String content;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime created;
    @TableLogic
    private Integer status;


}
