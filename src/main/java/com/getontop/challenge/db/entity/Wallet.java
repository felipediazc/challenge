package com.getontop.challenge.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 40)
    @NotNull
    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @Size(max = 40)
    @NotNull
    @Column(name = "lastname", nullable = false, length = 40)
    private String lastname;

    @Size(max = 40)
    @NotNull
    @Column(name = "routingnumber", nullable = false, length = 40)
    private String routingnumber;

    @Size(max = 40)
    @NotNull
    @Column(name = "nationalidnumber", nullable = false, length = 40)
    private String nationalidnumber;

    @Size(max = 40)
    @Column(name = "accountnumber", nullable = true, length = 40)
    private String accountnumber;

    @Size(max = 40)
    @NotNull
    @Column(name = "bankname", nullable = false, length = 40)
    private String bankname;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "accountid", nullable = false)
    private Account accountid;

}