package com.paymentsimple.service;

import com.paymentsimple.domain.Payment;
import com.paymentsimple.repository.PaymentRepository;
import com.paymentsimple.rest.PaymentController;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
@CacheConfig(cacheNames={"payments"})
public class PaymentServiceImpl implements PaymentService{

    private PaymentRepository paymentRepository;

    private final Logger logger = LoggerFactory.getLogger(PaymentController.class);


    @Override
    public List<Payment> listAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    @Cacheable(value = "payments", key = "#id")
    public  Payment listPaymentsById(Long id) {
        logger.info("Call list Payments By {}", id);
        return  paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    @Override
    public Payment processPayment(Payment payment) {
        var generatedId = new Random().nextLong(20000);
        payment.setPaymentNumber(generatedId);
        payment.setPaymentDate(LocalDate.now());

        return paymentRepository.save(payment);
    }

    @Override
    @CachePut(value = "payments", key = "#id")
    public Payment updatePayment(Long id, Payment payment) {
        logger.info("Update Payment {}", id);

        var paymentFromDb = paymentRepository.findById(id);

        if(paymentFromDb.isPresent()){
            var paymentUpdate = paymentFromDb.map(p -> payment);

            return paymentRepository.save(paymentUpdate.get());
        }

        return null;
    }

    @Override
    @CacheEvict(key = "#id", value = "payments")
    public void deletePaymentsById(Long id) {
        paymentRepository.deleteById(id);
    }
}
