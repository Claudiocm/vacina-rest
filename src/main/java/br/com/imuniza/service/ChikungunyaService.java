package br.com.imuniza.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.imuniza.exception.RegistroDuplicadoException;
import br.com.imuniza.exception.RegistroNaoEncontradoException;
import br.com.imuniza.modelo.Chikungunia;
import br.com.imuniza.repository.ChikungunyaRepository;

@Service
public class ChikungunyaService {
	@Autowired
	ChikungunyaRepository repository;

	public Page<Chikungunia> buscarTodos() {
		return findPage(1,5,"sintomas","ASC");
	}
	
	public Chikungunia cadastrar(Chikungunia chiku) {
		if(chiku != null && chiku.getCodigo() != 0 && repository.getOne(chiku.getCodigo()) != null) {
			throw new RegistroDuplicadoException("Registro já existe");
		}
		return repository.save(chiku);
	}
	
	public Chikungunia atualizar(Chikungunia chiku) {
		Chikungunia c = buscarPorId(chiku.getCodigo());
		
		return repository.save(c);
	}
	
	public Chikungunia buscarPorId(Integer id) {
		Chikungunia c = repository.findById(id).get();
		if (c == null) {
			throw new RegistroNaoEncontradoException("Caso não encontrado com este id");
		}
		return c;
	}
	
	public long retornaTotalCasos() {
		return repository.count();
	}
	
	
	public Page<Chikungunia> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
}
