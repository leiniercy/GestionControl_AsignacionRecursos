package com.example.application.data.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Trabajdor")
public class Trabajador extends Persona  {

    @EqualsAndHashCode.Include
    @ToString.Include
    
    @NotEmpty
    @NotBlank(message = "campo vacío")
    @Column(name = "solapin",unique = true, nullable = false)
    private String solapin;
    
    @NotNull(message = "debe elegir un campo")
    @ManyToOne
    private Area area;
    
    public String getStringNombreApellidos(){
        return getNombre() +" "+getApellidos();
    }
}
