package br.com.imuniza.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.imuniza.modelo.Chikungunia;
import br.com.imuniza.service.ChikungunyaService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/chikungunyas")
public class ChikungunyaController {
	@Autowired
	private ChikungunyaService service;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Chikungunia>> findAll() {
		Page<Chikungunia> list = service.buscarTodos();
		//List<Chikungunya> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Chikungunia> buscarId(@PathVariable Integer id) {
		Chikungunia obj = service.buscarPorId(id);
		return ResponseEntity.ok().body(obj);
	}

	/*
	 * @RequestMapping(value="/email", method=RequestMethod.GET) public
	 * ResponseEntity<Chikungunya> buscarPorEmail(@RequestParam(value="value")
	 * String email) { Chikungunya obj = service.findByEmail(email); return
	 * ResponseEntity.ok().body(obj); }
	 */

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> cadastrar(Chikungunia chiku) {
		Chikungunia obj = service.buscarPorId(chiku.getCodigo());
		obj = service.atualizar(chiku);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getCodigo())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> atualizar(@RequestBody Chikungunia chiku, @PathVariable Integer id) {
		Chikungunia obj = service.buscarPorId(chiku.getCodigo());
		obj = service.atualizar(obj);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value="/page", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Chikungunia>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="10") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="sintomas") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Chikungunia> list = service.findPage(page, linesPerPage, orderBy, direction);
		//Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));  
		return ResponseEntity.ok().body(list);
	}	

}
