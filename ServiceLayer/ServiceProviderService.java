package com.ama.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ama.custom_exceptions.ResourceNotFoundException;
import com.ama.dto.ApiResponse;
import com.ama.dto.ServiceProviderDTO;
import com.ama.entities.ServiceProvider;
import com.ama.entities.User;
import com.ama.repository.ServiceProviderRepository;
import com.ama.repository.UserRepository;

@Service
@Transactional
public class ServiceProviderServiceImpl implements ServiceProviderService{

	@Autowired
	private ServiceProviderRepository providerRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepository userRepository;
	
	
	
	@Override
	public List<ServiceProvider> listAllServiceProviders() {
		return providerRepository.findAll() //List<Category>
				.stream() //Stream<Category>
				.map(provider -> 
				modelMapper.map(provider,ServiceProvider.class)) //Stream<dto>
				.collect(Collectors.toList());
	}
	@Override
	public ApiResponse addNewProvider(ServiceProviderDTO newServiceProvider) {
		User providerId = userRepository
				.findById(newServiceProvider.getProviderId())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid provider id !!!!"));
		User providerName = userRepository
				.findByName(newServiceProvider.getProviderName())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid provider name !!!!"));
		//category, provider : persistent
		//map service dto --> entity
		ServiceProvider addServiceProvider = modelMapper.map(newServiceProvider, ServiceProvider.class);
		//establish E-R
		
		// user 1---->1 provider
		addServiceProvider.setProviderId(providerId);
		addServiceProvider.setProviderName(providerName);
		//=> success
		return new ApiResponse("new service provider added ");
	}
	@Override
	public String updateProvider(ServiceProvider serviceProvider) {
		providerRepository.save(serviceProvider);
		return "Service Provider Updated Succesfully";
	}
	@Override
	public String deleteProvider(Long id) {
		
		if (providerRepository.existsById(id)) {
			// API of CrudRepo - public void deleteById(ID id)
			providerRepository.deleteById(id);
			return "Service Provider details deleted";
		}
		return "deleting service provider details failed : Invalid Service Provider ID";
	}
	
}
