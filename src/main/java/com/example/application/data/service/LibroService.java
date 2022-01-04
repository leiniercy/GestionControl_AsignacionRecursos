package com.example.application.data.service;

import com.example.application.data.entity.Libro;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import com.example.application.data.repository.LibroRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
//@RequiredArgsConstructor
public class LibroService  {

   private LibroRepository repository;

    public LibroService(@Autowired LibroRepository repository) {
        this.repository = repository;
    }

    public Optional<Libro> get(Integer id) {
        return repository.findById(id);
    }

    public Libro update(Libro entity) {
        return repository.save(entity);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Page<Libro> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
