package br.com.imuniza.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	// @Value("${jwt.secret}")
	private static String secret = "d3s!s-pm$v-d!nf";
	// @Value("${jwt.expiration}")
	private static Long expiration = 3600L;

	public String generateToken(String username) {
		return Jwts.builder()
				.signWith(SignatureAlgorithm.HS256, secret)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))// data atual
				.setExpiration(new Date(System.currentTimeMillis() + expiration))// data expiração
				.compact();
	}

	public boolean tokenValido(String token) {
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		if (claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}

	public String getUsername(String token) {
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}

}
