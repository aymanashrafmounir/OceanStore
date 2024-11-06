package com.demo.sqlite.Entites;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class orders {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orderId")
    private int orderId;

    @Column(name = "companyId")
    @JoinColumn(name = "companyId", referencedColumnName = "companyId", insertable = false, updatable = false)
    private int companyId;

    @Column(name = "userId")
    private int userId;

    @Column(name = "status")
    private String status;
}

