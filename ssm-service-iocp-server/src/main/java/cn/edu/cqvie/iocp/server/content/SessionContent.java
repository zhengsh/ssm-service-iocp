package cn.edu.cqvie.iocp.server.content;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话上下文
 *
 * @author ZHENG SHAOHONG
 */
public class SessionContent {

    private static SessionContent instance = new SessionContent();

    private Map<String, ChannelId> sessionMap = new ConcurrentHashMap<>();
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private SessionContent() {
    }

    public static SessionContent getInstance() {
        return instance;
    }

    public Channel get(String token) {
        ChannelId channelId = sessionMap.get(token);
        return channelGroup.find(channelId);
    }

    public Channel get(ChannelId channelId) {
        return channelGroup.find(channelId);
    }

    public synchronized SessionContent set(String token, Channel channel) {
        channelGroup.add(channel);
        sessionMap.put(token, channel.id());
        return this;
    }

    public synchronized SessionContent remove(Channel channel) {
        channelGroup.remove(channel);
        return this;
    }

    public synchronized SessionContent remove(String token) {
        Channel channel = get(token);
        if (channel != null) {
            channelGroup.remove(channel);
            sessionMap.remove(token);
        }
        return this;
    }

    public synchronized int count() {
        return channelGroup.size();
    }
}
