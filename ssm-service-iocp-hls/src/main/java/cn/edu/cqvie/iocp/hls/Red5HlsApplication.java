package cn.edu.cqvie.iocp.hls;

import org.red5.spring.Red5ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Red5 Server
 *
 * @author zhengsh
 * @date 2020-11-02
 */
@SpringBootApplication
@ComponentScan(basePackages = "org.red5.spring")
public class Red5HlsApplication extends Red5ApplicationContext {

    public static void main(String[] args) {
        //启动服务器
        SpringApplication.run(Red5HlsApplication.class, args);
    }
}
