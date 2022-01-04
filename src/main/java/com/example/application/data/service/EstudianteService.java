package com.example.application.data.service;

import com.example.application.data.entity.Estudiante;
import com.example.application.data.repository.EstudianteRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;


@Service
@RequiredArgsConstructor
public class EstudianteService implements CrudListener<Estudiante> {
    
    private final EstudianteRepository repository;

    @Override
    public List<Estudiante> findAll() {
        return repository.findAll();
    }

    @Override
    public Estudiante add(Estudiante estudiante) {
        return repository.save(estudiante);
    }

    @Override
    public Estudiante update(Estudiante estudiante) {
         return repository.save(estudiante);
    }

    @Override
    public void delete(Estudiante estudiante) {
        repository.delete(estudiante);
    }

    

}
