package br.com.imuniza.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.imuniza.security.UsuarioSS;
import br.com.imuniza.service.UsuarioService;
import br.com.imuniza.util.JWTUtil;
import br.com.imuniza.util.UsuarioSecurity;


@RestController
@RequestMapping(value = "/auth")
public class AuthController {
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private UsuarioService service;
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UsuarioSS user = UsuarioService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}
	/*
	 * @RequestMapping(value = "/forgot", method = RequestMethod.POST) public
	 * ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
	 * service.sendNewPassword(objDto.getEmail()); return
	 * ResponseEntity.noContent().build(); }
	 */
}
