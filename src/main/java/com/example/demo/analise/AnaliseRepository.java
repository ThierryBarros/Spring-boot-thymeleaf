package com.example.demo.analise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnaliseRepository extends JpaRepository<Analise, Long>{

	List<Analise> findByCodigoAnalise(String codigo);

}
