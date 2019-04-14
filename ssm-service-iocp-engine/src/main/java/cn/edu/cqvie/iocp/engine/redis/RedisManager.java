package cn.edu.cqvie.iocp.engine.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;

/**
 * Redis 管理类
 *
 * @author ZHENG SHAOHONG
 */
public class RedisManager {

    private static RedisManager instance = new RedisManager();

    private RedisManager () {

    }

    public static RedisManager getInstance() {
        return instance;
    }

    public RedissonClient getRedissonClient() {
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        config.useReplicatedServers()
                // use "rediss://" for SSL connection
                .addNodeAddress("redis://127.0.0.1:6379");

        return Redisson.create(config);
    }
}
