package com.pichincha.exam.users.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "Account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {
    @Id
    private Long id;

    @Column("number")
    private String number;

    @Column("type")
    private String type;

    @Column("initial_value")
    private BigDecimal initial_value;

    @Column("status")
    private Boolean status;

}
