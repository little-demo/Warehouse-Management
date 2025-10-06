package com.antran.Warehouse_management.entity;

import com.antran.Warehouse_management.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "goods_receipts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoodsReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String receiptCode;
    LocalDateTime receiptDate;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    Supplier supplier;

    BigDecimal totalAmount; //tính tự động khi thêm mới hoặc cập nhật

    @ManyToOne
    @JoinColumn(name = "user_id")
    User createdBy;

    BigDecimal paidAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    PaymentStatus paymentStatus;

    @OneToMany(mappedBy = "goodsReceipt", cascade = CascadeType.ALL, orphanRemoval = true)
    List<GoodsReceiptDetail> details = new ArrayList<>();
}
