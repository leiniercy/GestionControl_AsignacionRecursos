package com.example.application.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Estudiantes")
public class Estudiante extends Persona {

    
    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(name = "solapin")
    private String solpain;
    @Column(name = "annoAcademico")
    private Integer anno_academico;

    @ManyToOne
    private Grupo grupo;
    
}
