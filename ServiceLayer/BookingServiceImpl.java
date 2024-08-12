package com.ama.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ama.dto.BookingDTO;
import com.ama.entities.Booking;
import com.ama.repository.BookingRepository;

@Service
@Transactional
public class BookingServiceImpl implements BookingService{

	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public List<BookingDTO> viewAllBooking() {
		return bookingRepository.findAll() //List<Category>
				.stream() //Stream<Category>
				.map(booking -> 
				modelMapper.map(booking,BookingDTO.class)) //Stream<dto>
				.collect(Collectors.toList());
	}

	@Override
	public Booking viewBooking(Long bookingId) {
		bookingRepository.findById(bookingId);
		return null;
	}

}
