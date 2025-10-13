package com.antran.Warehouse_management.entity;

import com.antran.Warehouse_management.enums.DebtStatus;
import com.antran.Warehouse_management.enums.DebtType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "partner_debts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PartnerDebt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "partner_id", nullable = false)
    Partner partner;

    LocalDateTime transactionDate;
    String description;
    BigDecimal totalAmount; //Tổng nợ hiện tại
    BigDecimal paidAmount; //Số tiền đã trả
    BigDecimal remainingAmount; //Số tiền còn nợ

    @Enumerated(EnumType.STRING)
    DebtType debtType;
    @Enumerated(EnumType.STRING)
    DebtStatus status;
}
