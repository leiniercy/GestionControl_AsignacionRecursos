package com.example.application.data.service;

import com.example.application.data.entity.TipoMaterial;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.application.data.repository.TipoMaterialRepository;

@Service
//@RequiredArgsConstructor
public class TipoMaterialService {
    
    private TipoMaterialRepository repository;
    
    public TipoMaterialService(@Autowired TipoMaterialRepository repository) {
        this.repository = repository;
    }

    public Optional<TipoMaterial> get(Integer id) {
        return repository.findById(id);
    }

    public TipoMaterial update(TipoMaterial entity) {
        return repository.save(entity);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Page<TipoMaterial> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }
    
}
