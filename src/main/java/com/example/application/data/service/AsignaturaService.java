/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.application.data.service;



import com.example.application.data.entity.Asignatura;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import com.example.application.data.repository.AsignaturaRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Leinier
 */
@Service
//@RequiredArgsConstructor
public class AsignaturaService{
    
    private  AsignaturaRepository repository;

    public AsignaturaService(@Autowired AsignaturaRepository repository) {
        this.repository = repository;
    }

    public Optional<Asignatura> get(Integer id) {
        return repository.findById(id);
    }

    public Asignatura update(Asignatura entity) {
        return repository.save(entity);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
    
    public void delete(Asignatura asignatura) {
        repository.delete(asignatura);
    }

    public Page<Asignatura> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public long count() {
        return  repository.count();
    }
    
}
