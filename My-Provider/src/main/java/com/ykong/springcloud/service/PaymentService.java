package com.ykong.springcloud.service;

import com.ykong.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PaymentService {

    int save(Payment payment);

    Payment getPaymentById(@Param("id") Long id);

    List<Payment> getAll();
}
