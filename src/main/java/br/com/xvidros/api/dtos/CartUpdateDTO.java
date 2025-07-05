package br.com.xvidros.api.dtos;

import br.com.xvidros.api.entities.User;

public record CartUpdateDTO(
			long id,
			User user
		) {}
