package com.zym.redis.core;

import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 循环监听队列中的任务
 */
@Component
public class JobTimer {

    /**
     * job tag
     */
    static final String JOBS_TAG = "customer_job_timer_jobs";

    Logger logger = LoggerFactory.getLogger(JobTimer.class);

    @Resource
    private RedissonClient client;

    @Resource
    private ApplicationContext context;


    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    @PostConstruct
    public void startJobTimer() {
        RBlockingQueue<JobBody> blockingQueue = client.getBlockingQueue(JOBS_TAG);
        //开启线程监听队列中到期的任务
        new Thread(() -> {
            while (true) {
                try {
                    //从redis队列中获取到时的消息
                    JobBody job = blockingQueue.take();
                    logger.info("获取延时任务{}：" , job.getJobParams().toString());
                    //执行到时的任务
                    executorService.execute(new ExecutorTask(context, job));
                } catch (Exception e) {
                    logger.error("消息执行异常{}：",Arrays.toString(e.getStackTrace()));
                    try {
                        TimeUnit.SECONDS.sleep(60);
                    } catch (Exception ex) {
                        logger.error("消息执行异常{}：", Arrays.toString(ex.getStackTrace()));
                    }
                }
            }
        }).start();
    }

    static class ExecutorTask implements Runnable {

        private final ApplicationContext context;

        private final JobBody delayJob;

        public ExecutorTask(ApplicationContext context, JobBody delayJob) {
            this.context = context;
            this.delayJob = delayJob;
        }

        @Override
        public void run() {
            //从容器中获取需要被执行的业务bean
            ExecutorJob service = (ExecutorJob) context.getBean(delayJob.getAClass());
            service.execute(delayJob);
        }
    }
}

