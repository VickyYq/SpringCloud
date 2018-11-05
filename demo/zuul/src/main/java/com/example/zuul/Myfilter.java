package com.example.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class Myfilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(Myfilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    //        shouldFilter：这里可以写逻辑判断，是否要过滤，本文true,永远过滤。
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
//        过滤器之间并不会直接进行通信，而是通过RequestContext来共享信息
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        Object accessToken = request.getParameter("token");
        if (accessToken == null) {
            log.warn("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty");
            } catch (Exception e) {
            }

            return null;
        }
        log.info("ok");
        return null;
    }
}

//PRE过滤器: 在请求被路由之前调用, 可用来实现身份验证、在集群中选择请求的微服务、记录调试信息等;
//ROUTING过滤器: 在路由请求时候被调用;
//POST过滤器: 在路由到微服务以后执行, 可用来为响应添加标准的HTTP Header、收集统计信息和指标、将响应从微服务发送给客户端等;
//ERROR过滤器: 在处理请求过程时发生错误时被调用。


