/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.application.data.service;

import com.example.application.data.entity.TarjetaPrestamo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import com.example.application.data.repository.TarjetaPrestamoRepository;
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
public class TarjetaPrestamoService {

    private TarjetaPrestamoRepository repository;

    public TarjetaPrestamoService(@Autowired  TarjetaPrestamoRepository repository) {
        this.repository = repository;
    }

    public Optional<TarjetaPrestamo> get(Integer id) {
        return repository.findById(id);
    }

    public TarjetaPrestamo update(TarjetaPrestamo entity) {
        return repository.save(entity);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Page<TarjetaPrestamo> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }
}
