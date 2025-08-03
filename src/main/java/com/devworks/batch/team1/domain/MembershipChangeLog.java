package com.devworks.batch.team1.domain;

import com.devworks.batch.team1.Rank;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MembershipChangeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rank prev_rank;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private  Rank post_rank;

    @Column(nullable = false)
    private LocalDate trace_time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
