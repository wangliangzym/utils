package com.zym;

import com.zym.redis.core.JobBody;
import com.zym.redis.core.DelayJobService;
import com.zym.consumer.Demo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@EntityScan(basePackages = "com.zym.delay")
class RedisDelayApplicationTests {

    @Autowired
    DelayJobService delayJobService;

    @Test
    void contextLoads() {
    }

    @Test
    void test1(){
        JobBody delayJob = new JobBody();
        Map param = new HashMap();
        param.put("id",1);
        param.put("name","xiaoming");
        delayJob.setJobParams(param);
        delayJob.setAClass(Demo.class);
        System.out.println("开始执行延时任务");
        delayJobService.submitJob(delayJob,10L, TimeUnit.SECONDS);
    }

}
