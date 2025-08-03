package com.devworks.batch.team2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rank currRank;

    public User(Rank currRank) {
        this.currRank = currRank;
    }

    public User() {

    }

    // 선택: 등급 변경 로그와 거래 로그를 양방향으로 설정하고 싶을 경우 사용
    // @OneToMany(mappedBy = "user")
    // private List<MembershipChangeLog> membershipChangeLogs;

    // @OneToMany(mappedBy = "seller")
    // private List<SalesLog> salesAsSeller;

    // @OneToMany(mappedBy = "buyer")
    // private List<SalesLog> salesAsBuyer;

    // 생성자, Getter, Setter
}
