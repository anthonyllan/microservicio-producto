package com.tec.producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tec.producto.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{

}
