package com.example.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("service-hi")
public interface controllerapi {
    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    String sayhi(@RequestParam(value = "name") String name);
}
