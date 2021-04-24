package com.zym.redis.core;

import lombok.Data;

import java.util.Map;

/**
 * 延时任务消息体
 */
@Data
public class JobBody {

    /**
     * 业务key
     */
    private String bizKey;
    /**
     * 参数封装map
     */
    private Map<String,Object> jobParams;
    /**
     * 需要被执行的任务的class
     */
    private Class aClass;
}
