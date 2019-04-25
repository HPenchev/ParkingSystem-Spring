package com.parking.services;

public interface ParkingService {
	void createParking(int numberOfLevels, 
			int carPlacesPerLevel, 
			int busPlacesPerLevel, 
			int motorcyclePlacesPerLevel, 
			int numberOfentries, 
			int numberOfExits);
}
