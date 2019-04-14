### ssm-service-iocp

## Message Server


1.实现消息的推送，可以指定个终端推送；

2.客户端自动断线重连；

3.支持分布式，消息支持设置最后发送时间【实现中】；

4.增加监控页面，可以做基础配置和消息监控【待实现】；


## 使用中间件

1.netty 作为Socket通讯的核心组件；

2.slf4j 和 log4j2 作为日志组件；

3.使用 redisson 组件来作为redis链接组件；

4.工具类组件 fastjson, lombok 等。


## 设计目标

1.简单小巧、单机支持2w连接。