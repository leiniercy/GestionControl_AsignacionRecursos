/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.application.data.service;


import com.example.application.data.entity.Grupo;
import com.example.application.data.repository.GrupoRepository;
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
public class GrupoService  {

    private GrupoRepository repository;

    public GrupoService(@Autowired GrupoRepository repository) {
        this.repository = repository;
    }

    public Optional<Grupo> get(Integer id) {
        return repository.findById(id);
    }

    public Grupo update(Grupo entity) {
        return repository.save(entity);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
    
    public void delete(Grupo grupo) {
        repository.delete(grupo);
    }

    public Page<Grupo> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public long count() {
        return  repository.count();
    }

}
