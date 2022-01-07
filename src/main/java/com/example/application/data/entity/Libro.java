package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "Libros")
public class Libro extends AbstractEntity {

    @EqualsAndHashCode.Include
    @ToString.Include

    @NotNull(message = "campo vacío")
    @Column(name = "codigo", unique = true)
    private Integer codigo;

    @NotBlank(message = "campo vacío")
    @Column(name = "titulo", length = 250, nullable = false)
    private String titulo;

    @NotNull(message = "campo vacío")
    @Column(name = "volumen")
    private Integer volumen;

    @NotNull(message = "campo vacío")
    @Column(name = "tomo")
    private Integer tomo;

    @NotBlank(message = "campo vacío")
    @Column(name = "autor", length = 250, nullable = false)
    private String autor;

    @NotNull(message = "campo vacío")
    @Column(name = "precio")
    private Integer precio;

    @NotNull(message = "campo vacío")
    @Column(name = "totalLibros")
    private Integer cant_libros;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "libro")
    private List<TarjetaPrestamo> tarjetas;

}
