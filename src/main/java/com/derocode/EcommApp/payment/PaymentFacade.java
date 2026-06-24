package com.derocode.EcommApp.payment;


import com.derocode.EcommApp.payment.api.PaymentResponseDto;

public interface PaymentFacade {

    PaymentResponseDto getById(Long id);

}
