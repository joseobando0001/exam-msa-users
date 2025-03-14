package com.pichincha.exam.users.domain.entity;

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

    @Column("person_id")
    private Long personId;

    @Column("password")
    private String password;

    @Column("status")
    private Boolean status;

}

