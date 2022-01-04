package com.example.application.data.service;

import com.example.application.data.entity.Trabajador;
import com.example.application.data.repository.TrabajadorRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

@Service
//@RequiredArgsConstructor
public class TrabajadorService   {

    private final TrabajadorRepository repository;
    
  public TrabajadorService(@Autowired TrabajadorRepository repository) {
        this.repository = repository;
    }

    public Optional<Trabajador> get(Integer id) {
        return repository.findById(id);
    }

    public Trabajador update(Trabajador entity) {
        return repository.save(entity);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Page<Trabajador> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }


}
