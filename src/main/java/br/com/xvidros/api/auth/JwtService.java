package br.com.xvidros.api.auth;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import br.com.xvidros.api.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private final SecretKey secret = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	public String generateToken(User user) {
		return Jwts.builder()
				.setSubject(user.getEmail())
				.claim("name", user.getName())
				.claim("role", user.getRole())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 86400000))
				.signWith(secret)
				.compact();
	}
	
	public String extractEmail(String token) {
		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	public boolean isValid(String token, UserDetails user) {
		return extractEmail(token).equals(user.getUsername());
	}
	
}

