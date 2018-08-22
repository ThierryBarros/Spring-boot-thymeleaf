package com.example.demo.analise;


import org.springframework.data.jpa.repository.JpaRepository;

public interface AnaliseSensorialRepository extends JpaRepository<AnaliseSensorial, Long>{

	AnaliseSensorial findByCodigo(String codigoAnalise);

}


