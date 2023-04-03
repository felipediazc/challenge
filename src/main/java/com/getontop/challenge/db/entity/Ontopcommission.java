package com.getontop.challenge.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(name = "commissiondate", nullable = false)
    @CreationTimestamp
    private Instant commissiondate;

    @NotNull
    @Column(name = "comission", nullable = false)
    private Double comission;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @Size(max = 40)
    @NotNull
    @Column(name = "peertransactionid", nullable = false, length = 40)
    private String peertransactionid;

    @Size(max = 40)
    @NotNull
    @Column(name = "localtransactionid", nullable = false, length = 40)
    private String localtransactionid;

    @Size(max = 20)
    @NotNull
    @Column(name = "status", nullable = false, length = 20)
    private String status;

}