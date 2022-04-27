package com.ykong.springcloud.mapper;

import com.ykong.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaymentMapper {

    int save(Payment payment);

    Payment getPaymentById(@Param("id") Long id);

    List<Payment> getAll();
}
