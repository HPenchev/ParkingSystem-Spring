package com.parking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.models.ParkingLevel;
import com.parking.repositories.ParkingLevelRepository;

@Service
public class TestService {
	@Autowired
	ParkingLevelRepository parkingLevelRepository;
	
	public void save(ParkingLevel level) {
		parkingLevelRepository.save(level);
		
		List test = parkingLevelRepository.findAll();
		
		System.out.println(test.size());
	}
}
