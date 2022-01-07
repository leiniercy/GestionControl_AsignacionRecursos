/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.application.data;

/**
 *
 * @author Leinier
 */
import com.example.application.data.repository.*;
import com.example.application.data.entity.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataService {

    private AreaRepository areaRepository;
    private AsignaturaRepository asignaturaRepository;
    private EstudianteRepository estudianteRepository;
    private GrupoRepository grupoRepository;
    private LibroRepository libroRepository;
    private ModuloRepository moduloRepository;
    private PersonaRepository personaRepository;
    private RecursoMaterialRepository recursoMaterialRepository;
    private TarjetaPrestamoRepository tarjetaPrestamoRepository;
    private TipoMaterialRepository tipoMaterialRepository;
    private TrabajadorRepository trabajadorRepository;

    public DataService(
            @Autowired AreaRepository areaRepository, 
            @Autowired AsignaturaRepository asignaturaRepository, 
            @Autowired EstudianteRepository estudianteRepository, 
            @Autowired GrupoRepository grupoRepository,
            @Autowired LibroRepository libroRepository, 
            @Autowired ModuloRepository moduloRepository, 
            @Autowired PersonaRepository personaRepository, 
            @Autowired RecursoMaterialRepository recursoMaterialRepository, 
            @Autowired TarjetaPrestamoRepository tarjetaPrestamoRepository,
            @Autowired TipoMaterialRepository tipoMaterialRepository, 
            @Autowired TrabajadorRepository trabajadorRepository) {
        
        this.areaRepository = areaRepository;
        this.asignaturaRepository = asignaturaRepository;
        this.estudianteRepository = estudianteRepository;
        this.grupoRepository = grupoRepository;
        this.libroRepository = libroRepository;
        this.moduloRepository = moduloRepository;
        this.personaRepository = personaRepository;
        this.recursoMaterialRepository = recursoMaterialRepository;
        this.tarjetaPrestamoRepository = tarjetaPrestamoRepository;
        this.tipoMaterialRepository = tipoMaterialRepository;
        this.trabajadorRepository = trabajadorRepository;
    }
    //Area
    public List<Area> findAllArea() {
        return areaRepository.findAll();
    }

    public long countArea() {
        return areaRepository.count();
    }

    public void deleteArea(Area area) {
        areaRepository.delete(area);
    }

    public void saveArea(Area area) {
        if (area == null) {
            System.err.println("This field is null. Are you sure you have connected your form to the application?");
            return;
        }
        areaRepository.save(area);
    }
    
    //Asignatura
    public List<Asignatura> findAllAsignatura() {
        return asignaturaRepository.findAll();
    }

    public long countAsignatura() {
        return asignaturaRepository.count();
    }

    public void deleteAsignatura(Asignatura asignatura) {
        asignaturaRepository.delete(asignatura);
    }

    public void saveAsignatura(Asignatura asignatura) {
        if (asignatura == null) {
            System.err.println("This field is null. Are you sure you have connected your form to the application?");
            return;
        }
        asignaturaRepository.save(asignatura);
    }
    
    //Estudiante
    public List<Estudiante> findAllEstudiante() {
             return estudianteRepository.findAll();
    }
    
    public long countEstudiante() {
        return estudianteRepository.count();
    }

    public void deleteEstudiante(Estudiante estudiante) {
        estudianteRepository.delete(estudiante);
    }

    public void saveEstudiante(Estudiante estudiante) {
        if (estudiante == null) {
            System.err.println("This field is null. Are you sure you have connected your form to the application?");
            return;
        }
        estudianteRepository.save(estudiante);
    }
    
    //Grupo
    public List<Grupo> findAllGrupo() {
        return grupoRepository.findAll();
    }
    
    public long countGrupo() {
        return grupoRepository.count();
    }

    public void deleteGrupo(Grupo grupo) {
        grupoRepository.delete(grupo);
    }

    public void saveGrupo(Grupo grupo) {
        if (grupo == null) {
            System.err.println("This field is null. Are you sure you have connected your form to the application?");
            return;
        }
        grupoRepository.save(grupo);
    }
    
    //Libro
    public List<Libro> findAllLibro() {
        return libroRepository.findAll();
    }
    
    public long countLibro() {
        return libroRepository.count();
    }

    public void deleteLibro(Libro libro) {
        libroRepository.delete(libro);
    }

    public void saveLibro(Libro libro) {
        if (libro == null) {
            System.err.println("This field is null. Are you sure you have connected your form to the application?");
            return;
        }
        libroRepository.save(libro);
    }
    
    //Modulo
    public List<Modulo> findAllModulo() {
        return moduloRepository.findAll();
    }
    
    public long countModulo() {
        return moduloRepository.count();
    }

    public void deleteModulo(Modulo modulo) {
        moduloRepository.delete(modulo);
    }

    public void saveModulo(Modulo modulo) {
        if (modulo == null) {
            System.err.println("This field is null. Are you sure you have connected your form to the application?");
            return;
        }
        moduloRepository.save(modulo);
    }
    
    //Persona
    public List<Persona> findAllPerson() {
        return personaRepository.findAll();
    }
    
    public long countPerson() {
        return personaRepository.count();
    }

    public void deletePerson(Persona person) {
        personaRepository.delete(person);
    }

    public void savePerson(Persona person) {
        if (person == null) {
            System.err.println("This field is null. Are you sure you have connected your form to the application?");
            return;
        }
        personaRepository.save(person);
    }
    
    //RecursoMaterial
    public List<RecursoMaterial> findAllRecursoMaterial() {
        return recursoMaterialRepository.findAll();
    }
    
    public long countRecursoMaterial() {
        return recursoMaterialRepository.count();
    }

    public void deleteRecursoMaterial(RecursoMaterial recursoMaterial) {
        recursoMaterialRepository.delete(recursoMaterial);
    }

    public void saveRecursoMaterial(RecursoMaterial recursoMaterial) {
        if (recursoMaterial == null) {
            System.err.println("This field is null. Are you sure you have connected your form to the application?");
            return;
        }
        recursoMaterialRepository.save(recursoMaterial);
    }
    
    //TarjetaPrestamo
    public List<TarjetaPrestamo> findAllTarjetaPrestamo() {
        return tarjetaPrestamoRepository.findAll();
    }
    
    public long countTarjetaPrestamo() {
        return tarjetaPrestamoRepository.count();
    }

    public void deleteTarjetaPrestamo(TarjetaPrestamo tarjetaPrestamo) {
        tarjetaPrestamoRepository.delete(tarjetaPrestamo);
    }

    public void saveTarjetaPrestamo(TarjetaPrestamo tarjetaPrestamo) {
        if (tarjetaPrestamo == null) {
            System.err.println("This field is null. Are you sure you have connected your form to the application?");
            return;
        }
        tarjetaPrestamoRepository.save(tarjetaPrestamo);
    }
    
    //TipoMaterial
    public List<TipoMaterial> findAllTipoMaterial() {
        return tipoMaterialRepository.findAll();
    }
    
    public long countTipoMaterial() {
        return tipoMaterialRepository.count();
    }

    public void deleteTipoMaterial(TipoMaterial tipoMaterial) {
        tipoMaterialRepository.delete(tipoMaterial);
    }

    public void saveTipoMaterial(TipoMaterial tipoMaterial) {
        if (tipoMaterial == null) {
            System.err.println("This field is null. Are you sure you have connected your form to the application?");
            return;
        }
        tipoMaterialRepository.save(tipoMaterial);
    }
    
    //Trabajador
    public List<Trabajador> findAllTrabajador() {
        return trabajadorRepository.findAll();
    }
    
    public long countTrabajador() {
        return trabajadorRepository.count();
    }

    public void deleteTrabajador(Trabajador trabajador) {
        trabajadorRepository.delete(trabajador);
    }

    public void saveTrabajador(Trabajador trabajador) {
        if (trabajador == null) {
            System.err.println("This field is null. Are you sure you have connected your form to the application?");
            return;
        }
        trabajadorRepository.save(trabajador);
    }
    
    
    
    
}
