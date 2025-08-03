package com.devworks.batch.team2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class SalesLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;

    @Column(nullable = false)
    private LocalDate transactionTime;

    @Column(nullable = false)
    private Integer transactionCost;

    // 생성자, Getter, Setter

    public SalesLog(User seller, User buyer, LocalDate transactionTime, int transactionCost) {
        this.seller = seller;
        this.buyer = buyer;
        this.transactionTime = transactionTime;
        this.transactionCost = transactionCost;
    }
}
