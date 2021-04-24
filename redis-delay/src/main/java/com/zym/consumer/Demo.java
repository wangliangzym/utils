package com.zym.consumer;

import com.zym.redis.core.JobBody;
import com.zym.redis.core.ExecutorJob;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 延时执行的具体的业务逻辑
 */
@Component
public class Demo implements ExecutorJob {
    @Override
    public void execute(JobBody delayJob) {
        Map<String,Object> jobParams = delayJob.getJobParams();
        System.out.println(jobParams.get("id"));
    }
}
