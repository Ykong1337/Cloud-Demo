package com.ykong.springcloud.controller;

import com.ykong.springcloud.entities.CommonResult;
import com.ykong.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderController {

    @Resource
    RestTemplate restTemplate;

    public static final String Payment_url = "http://cloud-gateway";

    @GetMapping("/consumer/payment/save")
    public CommonResult save(Payment payment) {
        return restTemplate.postForObject(Payment_url + "/payment/save", payment, CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(Payment_url + "/payment/get/" + id, CommonResult.class);
    }

    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonResult getPayment2(@PathVariable("id") Long id) {
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(Payment_url + "/payment/get/" + id, CommonResult.class);

        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        } else {
            return new CommonResult(400, "查询失败，不存在");
        }
    }

    @GetMapping("consumer/payment/getAll")
    public CommonResult getAll() {
        return restTemplate.getForObject(Payment_url + "/payment/getAll", CommonResult.class);
    }


}
