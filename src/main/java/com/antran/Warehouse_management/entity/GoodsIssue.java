package com.antran.Warehouse_management.entity;

import com.antran.Warehouse_management.enums.IssueType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
    Date issueDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "issue_type")
    IssueType issueType;
    BigDecimal totalAmount; //tính tự động khi thêm mới hoặc cập nhật

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    User createdBy;

    @OneToMany(mappedBy = "goodsIssue", cascade = CascadeType.ALL)
    List<GoodsIssueDetail> details;
}
