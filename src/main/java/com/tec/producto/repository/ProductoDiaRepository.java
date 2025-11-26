package com.tec.producto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tec.producto.entity.DiaSemana;
import com.tec.producto.entity.ProductoDia;

@Repository
public interface ProductoDiaRepository extends JpaRepository<ProductoDia, Long>{
	List<ProductoDia> findByProductoId(Long productoId);
}
