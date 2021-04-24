package com.zym.redis.core;

import com.sun.istack.internal.NotNull;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 延时任务提交
 */
@Component
public class DelayJobService {

    @Resource
    private RedissonClient client;

    /**
     * 提交延时任务方法
     * @param job 消息参数体
     * @param delay 延时时长
     * @param timeUnit 时间单位
     */
    public void submitJob(@NotNull JobBody job, @NotNull Long delay, @NotNull TimeUnit timeUnit){
        RBlockingQueue<JobBody> blockingQueue = client.getBlockingQueue(JobTimer.JOBS_TAG);
        RDelayedQueue<JobBody> delayedQueue = client.getDelayedQueue(blockingQueue);
        delayedQueue.offer(job,delay,timeUnit);
    }

}
