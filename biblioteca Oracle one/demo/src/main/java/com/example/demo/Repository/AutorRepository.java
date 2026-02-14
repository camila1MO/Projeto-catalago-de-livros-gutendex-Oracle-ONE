package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    
    // Busca simples para evitar duplicados
    Optional<Autor> findByNomeContainingIgnoreCase(String nome);

    // Query para encontrar autores vivos em um ano específico
    @Query("SELECT a FROM Autor a WHERE a.anoNascimento <= :ano AND (a.anoFalecimento IS NULL OR a.anoFalecimento > :ano)")
    List<Autor> buscarAutoresVivosNoAno(int ano);
}





