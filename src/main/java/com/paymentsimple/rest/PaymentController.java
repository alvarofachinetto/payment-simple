package com.paymentsimple.rest;

import com.paymentsimple.domain.Payment;
import com.paymentsimple.repository.PaymentRepository;
import com.paymentsimple.service.PaymentService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;


@RestController
@RequestMapping("/payments")
@AllArgsConstructor
public class PaymentController{

    private PaymentService paymentService;

    private final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @GetMapping
    public ResponseEntity<List<Payment>> listAllPayments(){

        var allPayments = paymentService.listAllPayments();

        return ResponseEntity.ok(allPayments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> listPaymentsById(@PathVariable Long id){
        var payment = paymentService.listPaymentsById(id);
        return ResponseEntity.ok(payment);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Payment> processPayment(@RequestBody Payment payment){
        paymentService.processPayment(payment);
        return ResponseEntity.status(201).body(payment);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody Payment payment){

        if(paymentService.updatePayment(id, payment) != null)
            return ResponseEntity.status(200).body(payment);

        return ResponseEntity.notFound().build();
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentsById(@PathVariable Long id){

        logger.info("Deleting payment is in process");

        paymentService.deletePaymentsById(id);

        return ResponseEntity.noContent().build();
    }



}
