/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.application.data.service;

import com.example.application.data.entity.Book;
import com.example.application.data.repository.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

/**
 *
 * @author Leinier
 */
@Service
@RequiredArgsConstructor
public class BookService implements CrudListener<Book>{

   
    private final BookRepository repository;


    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Override
    public Book add(Book book) {
        return repository.save(book);
    }

     @Override
    public Book update(Book book) {
        return repository.save(book);
    }

    @Override
    public void delete(Book book) {
        repository.delete(book);
    }
}
