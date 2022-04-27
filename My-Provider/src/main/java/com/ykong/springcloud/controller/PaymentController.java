package com.ykong.springcloud.controller;

import com.ykong.springcloud.entities.CommonResult;
import com.ykong.springcloud.entities.Payment;
import com.ykong.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    PaymentService paymentService;

    @Resource
    DiscoveryClient discoveryClient;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/payment/save")
    public CommonResult save(@RequestBody Payment payment) {
        int result = paymentService.save(payment);
        if (result > 0) {
            return new CommonResult(200, "插入成功,serverPort = " + serverPort, result);
        } else {
            return new CommonResult(400, "插入失败");
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment result = paymentService.getPaymentById(id);
        if (result != null) {
            return new CommonResult(200, "查询成功,serverPort = " + serverPort, result);
        } else {
            return new CommonResult(400, "查询失败，没有对应记录");
        }
    }

    @GetMapping("/payment/getHystrix/{id}")
    public Object getHystrix(@PathVariable("id") Long id) {
        return paymentService.getPaymentById(id);
    }

    @GetMapping("/payment/getAll")
    public CommonResult getAll() {
        List<Payment> result = paymentService.getAll();
        if (result != null) {
            return new CommonResult(200, "查询成功,serverPort = " + serverPort, result);
        } else {
            return new CommonResult(400, "查询失败，没有对应记录");
        }
    }

    @GetMapping("/payment/discovery")
    public Object discovery() {

        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("=====service: " + service + "\t" + serverPort);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("payment-service");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
        }

        return this.discoveryClient;
    }
}
