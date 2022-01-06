package com.example.application.data.repository;


import com.example.application.data.entity.TipoMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoMaterialRepository extends JpaRepository<TipoMaterial, Integer> {

}