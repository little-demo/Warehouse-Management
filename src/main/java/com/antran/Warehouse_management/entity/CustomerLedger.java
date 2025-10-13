package com.antran.Warehouse_management.entity;

import com.antran.Warehouse_management.enums.ReferenceType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_ledgers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerLedger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    LocalDateTime transactionDate;
    String description;
    BigDecimal debitAmount; //Số tiền KH nợ thêm (khi mua hàng)
    BigDecimal creditAmount; //Số tiền KH trả (khi thanh toán)

    @Enumerated(EnumType.STRING)
    @Column(name = "reference_type")
    private ReferenceType referenceType;
}