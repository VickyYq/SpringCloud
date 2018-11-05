package com.example.ribbon;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallback")
    public String hiService(String name) {
        return restTemplate.getForObject("http://SERVICE-HELLO/hello?name="+name,String.class);
    }

    public  String fallback(String name){
        return "fallback";
    }
}
