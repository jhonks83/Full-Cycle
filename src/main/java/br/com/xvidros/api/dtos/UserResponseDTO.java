package br.com.xvidros.api.dtos;

import br.com.xvidros.api.enuns.Role;
import java.time.Instant;

public record UserResponseDTO(
		long id,
		String name,
		String email, 
		Role role,
		Instant created_at,
		Instant updated_at
		) {}
