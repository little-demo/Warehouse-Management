package com.antran.Warehouse_management.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "goods_issues")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoodsIssue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String issueCode;
    LocalDateTime issueDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    String issueType;
    BigDecimal totalAmount; //tính tự động khi thêm mới hoặc cập nhật

    @ManyToOne
    @JoinColumn(name = "user_id")
    User createdBy;
}
