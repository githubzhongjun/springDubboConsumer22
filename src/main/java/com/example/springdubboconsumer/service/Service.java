package com.example.springdubboconsumer.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.springdubbo.service.IOrderService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.Inet4Address;
import java.net.UnknownHostException;

@RefreshScope //spring中的注解，表示当前类中的属性，需要动态刷新
@RestController
public class Service {

    @Value("${info:hello mic!}")
    public String info;

    @Autowired
    Environment environment;

    //loadbalance 负载策略的值是 LoadBalance接口实现类的name
    @DubboReference(cluster="failfast", loadbalance = "leastactive", check = false, mock = "com.example.springdubbo.service.IOrderServiceMock") //解决服务循环依赖，导致启动时抛异常
    IOrderService iOrderService;

    @GetMapping("/say")
    @SentinelResource(value = "say", fallback = "error")
    public String say(){
        return iOrderService.test("Mic");
    }

    @GetMapping("/cookie")
    @SentinelResource(value = "cookie", fallback = "error")
    public String cookie(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            System.out.println("当前客户端有cookie,不做其他处理");
        }else {
            Cookie cookie = new Cookie("name","zhongjun");
            response.addCookie(cookie);
            System.out.println("cookie保存成功");
        }

        return iOrderService.test("Mic");
    }

    @GetMapping("/loadblance")
    @SentinelResource(value = "loadblance", fallback = "error")
    public String loadblance(){
        String hostAddress = null;
        String property = null;

        try {
            hostAddress = Inet4Address.getLocalHost().getHostAddress();
            property = environment.getProperty("local.server.port");
            System.out.println("通过jdk获取地址："+hostAddress);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        return "当前请求的服务节点地址："+ hostAddress + " "+ property;
    }

    public String error(){
        return "淦 被限流了";
    }

    @GetMapping("/info")
    public String info(){
        return info;
    }

    @GetMapping("/task")
    public String task(){
        new Thread(() ->{

            while (true){
                try {
                    Thread.sleep(2000);
                    System.out.println("线程任务执行ing...");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }).start();

        return "正在导出，请稍后前往下载中心获取";
    }
}
