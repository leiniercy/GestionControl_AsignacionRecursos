/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 *
 * @author Leinier
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Modulos")
public class Modulo  extends AbstractEntity {

    @EqualsAndHashCode.Include
    @ToString.Include
    
    @NotNull(message = "campo vacío")
    @Column(name = "annoAcademico")
    private int annoAcademico;
    
    @OneToOne()
    private RecursoMaterial recurso;
    @OneToOne()
    private Estudiante estudiante;
    @OneToOne()
    private Trabajador trabajador;    
    @OneToOne()
    private Asignatura asignatura;
    
}
