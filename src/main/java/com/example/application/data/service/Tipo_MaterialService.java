package com.example.application.data.service;

import com.example.application.data.entity.Tipo_Material;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import com.example.application.data.repository.Tipo_MaterialRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
//@RequiredArgsConstructor
public class Tipo_MaterialService {
    
    private Tipo_MaterialRepository repository;
    
    public Tipo_MaterialService(@Autowired Tipo_MaterialRepository repository) {
        this.repository = repository;
    }

    public Optional<Tipo_Material> get(Integer id) {
        return repository.findById(id);
    }

    public Tipo_Material update(Tipo_Material entity) {
        return repository.save(entity);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Page<Tipo_Material> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }
    
}
