package com.pichincha.exam.users.domain.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "Movement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movement implements Serializable {
    @Id
    private Long id;

    @Column("date")
    private LocalDateTime date;

    @Column("type")
    private String type;

    @Column("value")
    private BigDecimal value;

    @Column("balance")
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

}
