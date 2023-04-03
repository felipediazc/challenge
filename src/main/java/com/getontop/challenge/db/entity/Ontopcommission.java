package com.getontop.challenge.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "ontopcommissions")
public class Ontopcommission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transactionid", nullable = false)
    private Transaction transactionid;

    @NotNull
    @Column(name = "commissiondate", nullable = false)
    private Instant commissiondate;

    @NotNull
    @Column(name = "comission", nullable = false)
    private Double comission;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

}