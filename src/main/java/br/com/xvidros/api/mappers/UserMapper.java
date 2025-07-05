package br.com.xvidros.api.mappers;

import br.com.xvidros.api.dtos.UserCreateDTO;
import br.com.xvidros.api.dtos.UserResponseDTO;
import br.com.xvidros.api.entities.User;
import br.com.xvidros.api.enuns.Role;

public class UserMapper {

	public static User toEntity(UserCreateDTO userCreateDTO) {
		
		User user = new User();
		user.setName(userCreateDTO.name());
		user.setEmail(userCreateDTO.email());
		user.setPassword(userCreateDTO.password());
		user.setRole(Role.CUSTOMER);
		
		return user;
	}
	
	public static UserResponseDTO toDTO(User user) {
		
		UserResponseDTO userResponse = new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getCreated_at(), user.getUpdated_at());
		
		return userResponse;
	}
	
}