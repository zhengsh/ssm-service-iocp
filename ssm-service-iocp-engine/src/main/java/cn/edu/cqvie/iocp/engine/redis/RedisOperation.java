package cn.edu.cqvie.iocp.engine.redis;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * redis 操作类
 *
 * @author ZHENG SHAOHONG
 */
public class RedisOperation implements Closeable {

    private static final Logger logger = LoggerFactory.getLogger(RedisOperation.class);

    private static RedisOperation instance = new RedisOperation();
    private RedissonClient redisson = RedisManager.getInstance().getRedissonClient();

    private RedisOperation() {
    }

    public static RedisOperation getInstance() {
        return instance;
    }

    /**
     * 设置值
     *
     * @param key   k
     * @param value v
     * @param timeToLive 过期时间单位:ms
     */
    public void set(String key, String value, long timeToLive) {
        redisson.getBucket(key).setAsync(value, timeToLive, TimeUnit.MILLISECONDS);
    }

    /**
     * 查询key
     *
     * @param key
     * @return
     */
    public String get(String key) {
        RBucket<String> bucket = redisson.getBucket(key);
        return bucket.get();
    }

    /**
     * 删除key
     *
     * @param key
     */
    public void remove(String key) {
        redisson.getBucket(key).unlinkAsync();
    }


    public static void main(String[] args) {
        RedisOperation redisOpt = RedisOperation.getInstance();
        String k = "HX_ZHANGSAN";
        redisOpt.set(k, "1000", 2000);

        String v = redisOpt.get(k);
        logger.info("get k:{}, v:{}", k, v);

    }

    @Override
    public void close() throws IOException {
        redisson.shutdown();
    }
}
