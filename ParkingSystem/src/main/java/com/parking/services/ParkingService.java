package com.parking.services;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.parking.resources.VehicleType;
import com.parking.resources.responses.ListOfParkingPlacesPerAvailability;
import com.parking.resources.responses.ParkVehicleResponse;

public interface ParkingService {
	void createParking(int numberOfLevels, 
			int carPlacesPerLevel, 
			int busPlacesPerLevel, 
			int motorcyclePlacesPerLevel, 
			int numberOfentries, 
			int numberOfExits);
	
	ResponseEntity<ParkVehicleResponse> parkVehicle(String vehicleNumber, VehicleType type);
	
	ResponseEntity<String> quit(String parkingPlaceNumber);
	
	Map<Integer, Map<VehicleType, ListOfParkingPlacesPerAvailability>> list();
}
