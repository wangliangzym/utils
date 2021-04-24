package com.zym.redis.core;

import com.zym.redis.core.JobBody;
import org.springframework.stereotype.Component;

/**
 * 任务执行接口：需要被执行的任务需要实现该接口
 */
@Component
public interface ExecutorJob {

    /**
     * 任务执行方法
     * @param delayJob 消息体
     */
    void execute(JobBody delayJob);
}
