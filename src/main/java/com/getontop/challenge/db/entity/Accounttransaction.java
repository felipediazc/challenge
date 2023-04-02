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
@Table(name = "accounttransactions")
public class Accounttransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "accountid", nullable = false)
    private Integer accountid;

    @Size(max = 100)
    @NotNull
    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @Size(max = 40)
    @NotNull
    @Column(name = "transactionid", nullable = false, length = 40)
    private String transactionid;

    @Column(name = "transactiondate", nullable = false)
    @CreationTimestamp
    private Instant transactiondate;

    @Size(max = 10)
    @NotNull
    @Column(name = "status", nullable = false, length = 10)
    private String status;

}