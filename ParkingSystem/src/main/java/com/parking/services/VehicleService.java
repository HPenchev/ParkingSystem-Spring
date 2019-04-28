package com.parking.services;

import java.util.Optional;

import com.parking.models.Vehicle;
import com.parking.resources.VehicleType;

public interface VehicleService {
	Vehicle create(String number, VehicleType type);
	
	Optional<Vehicle> findByVehicleNumber(String vehicleNumber);
	
	void updateVehicle(Vehicle vehicle);
}
