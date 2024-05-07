package com.paymentsimple.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long paymentNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private Double amount;

    private LocalDate createdAt;

    public String toString(){
        JSONObject json = new JSONObject();
        json.put("id", this.paymentNumber);
        json.put("paymentDate", this.paymentDate);
        json.put("type", this.paymentType);
        json.put("amount", this.amount);

        return json.toString();
    }

}
