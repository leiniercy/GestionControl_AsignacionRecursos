/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
@Table(name = " Tarjeta_de_Prestamos")
public class TarjetaPrestamo  extends AbstractEntity{

    @EqualsAndHashCode.Include
    @ToString.Include
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    @Column(name = "fecha_prestamo")
    private LocalDate fecha_prestamo;
    @Column(name = "fecha_recojida")
    private LocalDate fecha_recojida;
    
    @OneToOne()
    Estudiante estudiante;
    @OneToOne()
    Trabajador trabajador;
    @OneToMany(mappedBy = "tarjeta")
    List<Libro>libros;
    

}
