package com.getontop.challenge.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {
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
    @Column(name = "nationalnumber", nullable = false, length = 40)
    private String nationalnumber;

    @Size(max = 40)
    @NotNull
    @Column(name = "accountnumber", nullable = false, length = 40)
    private String accountnumber;

}