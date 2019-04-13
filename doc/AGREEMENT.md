# 平台协议

## 基本要求

1.传输格式：JSON. (计划后期采用 protocol buffers)

2.编码格式：UTF-8

3.业务字段命名要求：小驼峰形式（如：deviceId）

## TCP 接口

### 简述

1.使用接口一次发送数据的大小不应该超过4096kb字节

2.时间采用格林威治时间戳

3.设计模型：采用请求应答模型，平台做为服务端，各第三方系统做为客户端，客户端应定时向服务端发送心跳以维持连接。云平台终结点（IP:PORT）在正式应用前由平台方提供。

4.数据发送: 当客户端与服务端建立连接后，服务端可主动推送数据客户端，数据对齐方式采用1字节对齐；大小端模式采用大端模式；数据格式应遵循下表　定义：

5.数据定义: data 字段为业务数据，数据格式定义遵循和业务数据相同的数据定义。


// todo  