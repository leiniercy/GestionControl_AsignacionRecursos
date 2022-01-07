package com.example.application.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    
    @NotBlank(message = "campo vacío")
    @Column(name = "solapin",nullable = false ,unique = true)
    private String solpain;
    
    @NotNull(message = "campo vacío")
    @Column(name = "annoAcademico")
    private Integer anno_academico;

    @ManyToOne
    private Grupo grupo;
    
    public String getStringNombreApellidos(){
        return getNombre() +" "+getApellidos();
    }
    
    
}
