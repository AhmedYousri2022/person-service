package com.prodyna.person.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    @NotNull
    @Column(length = 20)
    private String name;
}
