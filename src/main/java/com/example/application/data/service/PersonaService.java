/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.application.data.service;

import com.example.application.data.entity.Persona;
import com.example.application.data.repository.PersonaRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

/**
 *
 * @author Leinier
 */
@Service
//@RequiredArgsConstructor
public class PersonaService  {
    
    private PersonaRepository repository;

    public PersonaService(@Autowired PersonaRepository repository) {
        this.repository = repository;
    }

    public Optional<Persona> get(Integer id) {
        return repository.findById(id);
    }

    public Persona update(Persona entity) {
        return repository.save(entity);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Page<Persona> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }


}
