package br.com.imuniza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.imuniza.modelo.Chikungunia;

@Repository
public interface ChikungunyaRepository extends JpaRepository<Chikungunia, Integer>{

}
