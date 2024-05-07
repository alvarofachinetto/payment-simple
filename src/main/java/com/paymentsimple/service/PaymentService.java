package com.paymentsimple.service;

import com.paymentsimple.domain.Payment;

import java.util.List;

public interface PaymentService {

    List<Payment> listAllPayments();

    Payment listPaymentsById(Long id);

    Payment processPayment(Payment payment);

    Payment updatePayment(Long id, Payment payment);

    void deletePaymentsById(Long id);

}
