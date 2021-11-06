package br.com.imuniza.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.imuniza.dto.UsuarioDTO;
import br.com.imuniza.exception.RegistroDuplicadoException;
import br.com.imuniza.exception.RegistroNaoEncontradoException;
import br.com.imuniza.modelo.Usuario;
import br.com.imuniza.repository.UsuarioRepository;
import br.com.imuniza.security.UsuarioSS;

@Service
public class UsuarioService implements UserDetailsService {
	@Autowired
	UsuarioRepository repository;

	public static UsuarioSS authenticated() {
		try {
			return (UsuarioSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}
	
	@Transactional(readOnly = true)
	public List<Usuario> buscarTodos() {
		return repository.findAll();
	}

	@Transactional(readOnly = false)
	public Usuario cadastrar(Usuario usuario) {
		if (usuario != null && usuario.getId()!= 0 && repository.findById(usuario.getId()) != null) {
			throw new RegistroDuplicadoException("Registro já existe");
		}
		if (usuario.getSenha() != null || !usuario.getSenha().isEmpty()) {
			String crypt = new BCryptPasswordEncoder().encode(usuario.getSenha());
			usuario.setSenha(crypt);
		}
		return repository.save(usuario);
	}
	
	public Usuario fromUsuarioDTO(UsuarioDTO usuario) {
		Usuario u = new Usuario(null, usuario.getNome(),usuario.getEmail(), usuario.getSenha(), usuario.getData_nascimento(),
				usuario.getTelefone(),usuario.getEndereco(),  usuario.getNumero(), usuario.getComplemento(), usuario.getCep()
				, usuario.getCidade(), usuario.getEstado());
		return  u;
	}

	@Transactional(readOnly = false)
	public Usuario atualizar(Usuario usuario) {
		Usuario c = buscarPorId(usuario.getId());
		if (usuario.getSenha() != null || !usuario.getSenha().isEmpty()) {
		   String crypt = new BCryptPasswordEncoder().encode(usuario.getSenha());
		   usuario.setSenha(crypt);
		}
		return repository.save(c);
	}

	@Transactional(readOnly = true)
	public Usuario buscarPorId(Long id) {
		Usuario c = repository.findById(id).get();
		if (c == null) {
			throw new RegistroNaoEncontradoException("Caso não encontrado com este id");
		}
		return c;
	}

	@Transactional(readOnly = true)
	public Usuario findByEmail(String email) {
		/*
		 * UsuarioSS user = UserService.authenticated(); if (user == null ||
		 * !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) { throw new
		 * AuthorizationException("Acesso negado"); }
		 */

		Usuario obj = repository.findByEmail(email);
		if (obj == null) {
			throw new RegistroNaoEncontradoException(
					"Usuário não encontrado! Tipo: " + Usuario.class.getName());
		}
		return obj;
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = findByEmail(username);
		return new UsuarioSS(usuario.getId(),usuario.getEmail(), usuario.getSenha(),usuario.getPerfis());
	}

	public static boolean isSenhaCorreta(String senhaDigitada, String senhaArmazenada) {
		return new BCryptPasswordEncoder().matches(senhaDigitada, senhaArmazenada);
	}

	@Transactional(readOnly = false)
	public void alterarSenha(Usuario usuario, String senha) {
		usuario.setSenha(new BCryptPasswordEncoder().encode(senha));
		repository.save(usuario);
	}

	@Transactional(readOnly = true)
	public Usuario buscarPorIdEPerfis(Long usuarioId, Long[] perfisId) {
		return repository.findByIdAndPerfis(usuarioId, perfisId)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário inexistente!"));
	}

}
