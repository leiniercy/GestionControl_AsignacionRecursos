package com.example.application.data.repository;


import com.example.application.data.entity.Tipo_Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Tipo_MaterialRepository extends JpaRepository<Tipo_Material, Integer> {

}