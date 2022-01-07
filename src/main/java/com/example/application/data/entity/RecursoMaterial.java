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
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
@Table(name = "RecursosMaterial")
public class RecursoMaterial extends AbstractEntity {

    @EqualsAndHashCode.Include
    @ToString.Include

    @NotNull(message = "campo vacío")
    @Column(name = "codigo", unique = true)
    private int codigo;

    @NotEmpty
    @NotBlank(message = "campo vacío")
    @Column(name = "nombre", nullable = false, length = 250)
    private String nombre;

    @OneToMany(mappedBy = "recurso")
    private List<TipoMaterial> materiales;

}
