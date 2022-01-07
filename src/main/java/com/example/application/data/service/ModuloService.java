/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.application.data.service;

import com.example.application.data.entity.Modulo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import java.util.List;
import com.example.application.data.repository.ModuloRepository;
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
public class ModuloService  {

    private  ModuloRepository repository;

    public ModuloService(@Autowired ModuloRepository repository) {
        this.repository = repository;
    }

    public Optional<Modulo> get(Integer id) {
        return repository.findById(id);
    }

    public Modulo update(Modulo entity) {
        return repository.save(entity);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
    
    public void delete(Modulo modulo) {
        repository.delete(modulo);
    }

    public Page<Modulo> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public long count() {
        return  repository.count();
    }

}
