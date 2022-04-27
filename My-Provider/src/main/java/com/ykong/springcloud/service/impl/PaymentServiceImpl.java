package com.ykong.springcloud.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.ykong.springcloud.entities.Payment;
import com.ykong.springcloud.mapper.PaymentMapper;
import com.ykong.springcloud.service.PaymentService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    PaymentMapper paymentMapper;

    @Override
    public int save(Payment payment) {
        return paymentMapper.save(payment);
    }

    @Override
    @SneakyThrows
    @HystrixCommand(fallbackMethod = "paymentTimeout", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
    })
    public Payment getPaymentById(Long id) {
        if (id < 0) {
            throw new RuntimeException("==========id不能为负数");
        }
        return paymentMapper.getPaymentById(id);
    }

    public Payment paymentTimeout(Long id) {
        return new Payment(id, "ID不能为负数，请重试");
    }

    @Override
    public List<Payment> getAll() {
        return paymentMapper.getAll();
    }
}
