package com.pichincha.exam.users.domain.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Table(name = "Client")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client implements Serializable {
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "persona_id", nullable = false)
    private Person person;

    @Column("password")
    private String password;

    @Column("status")
    private Boolean status;

}

