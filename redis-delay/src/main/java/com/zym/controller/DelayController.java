package com.zym.controller;

import com.zym.redis.core.JobBody;
import com.zym.redis.core.DelayJobService;
import com.zym.consumer.Demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("delay")
public class DelayController {

    @Autowired
    DelayJobService delayJobService;

    @RequestMapping("test")
    public String getDelay(){
        JobBody delayJob = new JobBody();
        Map<String, Object> param = new HashMap();
        param.put("id",1);
        param.put("name","xiaoming");
        delayJob.setJobParams(param);
        delayJob.setAClass(Demo.class);
        System.out.println("开始执行延时任务");
        delayJobService.submitJob(delayJob,5L, TimeUnit.SECONDS);

        return null;
    }
}
