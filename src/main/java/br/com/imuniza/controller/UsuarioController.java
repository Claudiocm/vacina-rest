package br.com.imuniza.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.imuniza.dto.UsuarioDTO;
import br.com.imuniza.modelo.Usuario;
import br.com.imuniza.security.UsuarioSS;
import br.com.imuniza.service.UsuarioService;
import br.com.imuniza.util.JWTUtil;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;
	@Autowired
	private JWTUtil jwt;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> salvar(@RequestBody UsuarioDTO usuario) {	
		Usuario u = usuarioService.fromUsuarioDTO(usuario);
		u = usuarioService.cadastrar(u);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(u.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> alterarUsuario(@RequestBody Usuario usuario) {
	   Usuario u = usuarioService.atualizar(usuario);
		return new ResponseEntity<Usuario>(u, HttpStatus.ACCEPTED);

	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Usuario>> listar() {
		return ResponseEntity.status(HttpStatus.OK).body(usuarioService.buscarTodos());
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> buscarPorId(@PathVariable("id") Long id) {
		Usuario usuario = null;
		usuario = usuarioService.buscarPorId(id);

		return ResponseEntity.status(HttpStatus.OK).body(usuario);
	}

	@GetMapping(value = "/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> buscarPorEmail(@PathVariable("email") String email) {
		Usuario usuario = usuarioService.findByEmail(email);
		return ResponseEntity.status(HttpStatus.OK).body(usuario);

	}

	/*
	 * @RequestMapping(value = "/publica/login", method =
	 * RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE) public
	 * ResponseEntity<Usuario> autenticar(Usuario usuario) {
	 * 
	 * Usuario usuario = usuarioService.autenticar(usuario.getEmail(), usuario.getSenha()); String token =
	 * jwt.generateToken(usuario.getEmail()); System.out.println(token); return
	 * ResponseEntity.status(HttpStatus.ACCEPTED).build(); }
	 */

}
