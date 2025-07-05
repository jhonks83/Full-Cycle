package br.com.xvidros.api.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.xvidros.api.dtos.UserCreateDTO;
import br.com.xvidros.api.dtos.UserResponseDTO;
import br.com.xvidros.api.dtos.UserUpdateDTO;
import br.com.xvidros.api.entities.Address;
import br.com.xvidros.api.entities.User;
import br.com.xvidros.api.mappers.UserMapper;
import br.com.xvidros.api.repository.AddressRepository;
import br.com.xvidros.api.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressRepository addressRepository;
		
	public UserResponseDTO store(UserCreateDTO userCreateDTO) {
		
		User user = UserMapper.toEntity(userCreateDTO);
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		User userResponse = userRepository.save(user);
		
		//Address address = new Address();
		//address.setUser(userResponse);
		//addressRepository.save(address);
		
		return UserMapper.toDTO(userResponse);
	}
	
	public List<UserResponseDTO> list(){
				
		return userRepository.findAll().stream().map(UserMapper::toDTO).toList();
	}
	
	public UserResponseDTO show(long id) {
		
		User user = userRepository.findById(id).orElseThrow(
				() -> new RuntimeException("Usuário com o id "+id+" não foi encontrado.")
		);
			
		return UserMapper.toDTO(user);
	}
	
	public UserResponseDTO update(UserUpdateDTO userUpdateDTO) {
		
		User user = userRepository
				.findById(userUpdateDTO.id())
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado para alteração"));
		
		user.setName(userUpdateDTO.name());
		user.setEmail(userUpdateDTO.email());
		user.setRole(userUpdateDTO.role());
		user.setPassword(userUpdateDTO.password());
				
		
		return UserMapper.toDTO(userRepository.save(user));
	}
	
	public void destroy(long id) {
		
		User user = userRepository
				.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado para deleção"));
		
		userRepository.delete(user);
		
	}
}