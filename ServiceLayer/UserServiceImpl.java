package com.ama.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ama.custom_exceptions.InvalidDataException;
import com.ama.dto.UserDTO;
import com.ama.entities.User;
import com.ama.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<UserDTO> getAllUsers() {
		return userRepository.findAll() //List<Uset>
				.stream() //Stream<Category>
				.map(user -> 
				modelMapper.map(user,UserDTO.class)) //Stream<dto>
				.collect(Collectors.toList());
	}

	@Override
	public User registration(User newUser) {
		return userRepository.save(newUser);
	}

	@Override
	public User login(String email, String password) {
		User user= userRepository.
				findByEmailAndPassword(email, password).orElseThrow(() ->
		new InvalidDataException("Invalid Email or password !!!!"));
		//return "User Logged in successfully";
		return user; 
	}

	@Override
	public String edit(Long id) {
		User updateUser = userRepository.findById(id)
				.orElseThrow(()-> new InvalidDataException("Invalid Id : Data not updated"));
		userRepository.save(updateUser);
		return "Data updated successfully";
	}

	@Override
	public String delete(Long id) {
		User delUser = userRepository.findById(id)
				.orElseThrow(()-> new InvalidDataException("Invalid Id : Data not deleted"));
		userRepository.delete(delUser);
		return "Data deleted successfully";
	}

	@Override
	public User viewUser(Long userId) {
		
		User user = userRepository.findById(userId)
				.orElseThrow(()-> new InvalidDataException("Invalid Id : Data not found"));
		return user;
	}

}
