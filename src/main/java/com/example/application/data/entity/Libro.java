package com.example.application.data.entity;


import com.example.application.data.AbstractEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "Libros")
public class Libro  extends AbstractEntity{

    @EqualsAndHashCode.Include
    @ToString.Include
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    @Column(name = "codigo", unique = true)
    private Integer codigo;
    @Column(name = "titulo", length = 250)
    private String titulo;
    @Column(name = "volumen")
    private Integer volumen;
    @Column(name = "tomo")
    private Integer tomo;
    @Column(name = "autor")
    private String autor;
    @Column(name = "precio")
    private Integer  precio;
    @Column(name = "totalLibros")
    private Integer cant_libros;
    @Column(name = "image")
    private String image;

    @ManyToOne
    private TarjetaPrestamo tarjeta;
    
    
}
