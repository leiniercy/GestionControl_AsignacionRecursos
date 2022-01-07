/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
@Table(name = "Persona")
public class Persona  extends AbstractEntity{

    @EqualsAndHashCode.Include
    @ToString.Include

    @NotEmpty
    @Column(name = "nombre", length = 250,nullable = false)
    @NotBlank(message = "campo vacío")
    private String nombre;
    
    @NotEmpty
    @NotBlank(message = "campo vacío")
    @Column(name = "apellidos", length = 250,nullable = false)
    private String apellidos;
    
    @NotEmpty
    @NotBlank(message = "campo vacío")
    @Column(name = "CI" ,length = 11)
    @Size(max = 11, min = 11) 
    private String ci;
    
    @Email
    @NotEmpty
    @NotBlank(message = "campo vacío")
    @Column(name = "email")
    private String email;

}
