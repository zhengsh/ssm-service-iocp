package cn.edu.cqvie.iocp.server.quartz;

import cn.edu.cqvie.iocp.engine.config.SystemConfig;
import cn.edu.cqvie.iocp.engine.redis.RedisOperation;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

@Slf4j
public class HeartbeatTest implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("定时器任务执行:{}", new Date(System.currentTimeMillis()));
        JobDataMap map = jobExecutionContext.getMergedJobDataMap();
        String key = String.valueOf(map.get("key"));
        String value = String.valueOf(map.get("value"));

        log.info("参数值: key: {}, value:{}.", key, value);

        RedisOperation redisOperation = RedisOperation.getInstance();
        redisOperation.set(key, value, 10L);

        log.info("Message Server HeartBeat By Redis Success !");
    }
}