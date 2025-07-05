package br.com.xvidros.api.dtos;

import br.com.xvidros.api.enuns.Role;

public record UserUpdateDTO(
			long id,
			String name,
			String email, 
			String password,
			Role role
		) {}
