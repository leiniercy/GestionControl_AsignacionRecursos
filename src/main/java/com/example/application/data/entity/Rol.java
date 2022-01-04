/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.application.data.entity;

/**
 *
 * @author Leinier
 */

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

@AllArgsConstructor
@NoArgsConstructor

public enum Rol {
 
    //Roles
    ADMIN("admin"), //Administrador general del sistema 
    
    DECANA("decana"), //Máximo responsable del control interno de la facultad. 
    
    VISEDECANO_ADMINSTRACION_ECONOMIA("vicedecano_adminstracion"),//Máximo responsable de controlar el destino final de todos los recursos de la facultad. 
    
    ADMINSITRADOR_DOCENTE("administrador_docente"), /*Encargado de dar entrada y salida a los insumos de limpieza en el Libro de control de insumos. 
                                                    Es el máximo responsable de que estos recursos lleguen a su destino final correspondiente: 
                                                    Auxiliares Generales de Servicio o a las oficinistas.*/ 

    ASISTENTE_CONTROL("asistente_control"), /*Responsable de entregar los recursos materiales a los estudiantes y trabajadores. 
                                            Además, debe mantener actualizada y firmadas las actas de destino final.*/
    
    RESPONSABLE_ALAMACEN("responsable_almacen"), //Encargado de la entrega, recepción y control de los libros en el almacén. 
    
    USER("user"), // tarbajadores y estudiantes (Personal de la facultad al que le es entregado los recursos materiales)
    
    PERSONAL_SERVICIO("personal_servicio"); //personal al que le es entregado los insumos de limpieza. 
    
    private String rolname;

    public String getRolname() {
        return rolname;
    }

    public void setRolname(String rolname) {
        this.rolname = rolname;
    }
    
}