package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
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
@Table(name = "Tipo_Materiales")
public class TipoMaterial  extends AbstractEntity{

    @EqualsAndHashCode.Include
    @ToString.Include
    
    @NotBlank(message = "campo vacío")
    @Column(name = "nombre")
    private String nombre;
    
    @ManyToOne
    RecursoMaterial recurso;

}
