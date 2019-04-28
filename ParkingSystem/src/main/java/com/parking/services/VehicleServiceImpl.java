package com.parking.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.models.Vehicle;
import com.parking.repositories.VehicleRepository;
import com.parking.resources.VehicleType;

@Service
public class VehicleServiceImpl implements VehicleService {
	@Autowired
	VehicleRepository vehicleRepository;
	
	@Override
	public Vehicle create(String number, VehicleType type) {
		Vehicle vehicle = new Vehicle();
		vehicle.setVehicleNumber(number);
		vehicle.setVehicleType(type);
		return vehicleRepository.save(vehicle);
	}

	@Override
	public Optional<Vehicle> findByVehicleNumber(String vehicleNumber) {
		return Optional.ofNullable(vehicleRepository.findByVehicleNumber(vehicleNumber));
	}

	@Override
	public void updateVehicle(Vehicle vehicle) {
		vehicleRepository.save(vehicle);
	}
	
}
