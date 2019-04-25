package com.parking.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.models.Entrance;
import com.parking.models.Exit;
import com.parking.models.ParkingLevel;
import com.parking.models.ParkingPlace;
import com.parking.repositories.EntranceRepository;
import com.parking.repositories.ExitRepository;
import com.parking.repositories.ParkingLevelRepository;
import com.parking.repositories.ParkingPlaceRepository;
import com.parking.resources.VehicleType;

@Service
public class ParkingServiceImpl implements ParkingService {

	@Autowired
	private ParkingLevelRepository parkingLevelRepository;
	@Autowired
	private ParkingPlaceRepository parkingPlaceRepository;
	@Autowired
	private EntranceRepository entranceRepository;
	@Autowired 
	ExitRepository exitRepository;
	
	@Override
	public void createParking(int numberOfLevels, int carPlacesPerLevel, int busPlacesPerLevel,
			int motorcyclePlacesPerLevel, int numberOfEntrances, int numberOfExits) {
		createEntrances(numberOfEntrances);
		createExits(numberOfExits);
		
		List<ParkingLevel> levels = createLevels(numberOfLevels);
		
		createParkingPlaces(levels, carPlacesPerLevel, busPlacesPerLevel, motorcyclePlacesPerLevel);
	}

	private void createEntrances(int numberOfEntrances) {
		Set<Entrance> entrances = new HashSet<>();
		
		for (int i = 0; i < numberOfEntrances; i++) {
			Entrance entrance = new Entrance();
			entrance.setNumber(i);
			entrances.add(entrance);
		}
	
		entranceRepository.saveAll(entrances);
	}


	private void createExits(int numberOfExits) {
		Set<Exit> exits = new HashSet<>();
		
		for (int i = 0; i < numberOfExits; i++) {
			Exit exit = new Exit();
			exit.setNumber(i);
			exits.add(exit);
		}
		
		exitRepository.saveAll(exits);
	}

	private List<ParkingLevel> createLevels(int numberOfLevels) {
		List<ParkingLevel> levels = new ArrayList<>();
		
		for (int i = 0; i < numberOfLevels; i++ ) {
			ParkingLevel level = new ParkingLevel();
			level.setLevel(i);
		}
		
		return parkingLevelRepository.saveAll(levels);
	}

	private void createParkingPlaces(List<ParkingLevel> levels, 
			int carPlacesPerLevel, 
			int busPlacesPerLevel,
			int motorcyclePlacesPerLevel) {
		Collection<ParkingPlace> parkingPlaces = new HashSet<>();
		
		for (ParkingLevel level : levels) {
			createParkingPlacesPerLevelAndVehicleType(level, VehicleType.CAR, carPlacesPerLevel);
			createParkingPlacesPerLevelAndVehicleType(level, VehicleType.BUS, busPlacesPerLevel);
			parkingPlaces.addAll(createParkingPlacesPerLevelAndVehicleType(level, VehicleType.MOTORCYCLE, motorcyclePlacesPerLevel));
		}
		
		parkingPlaceRepository.saveAll(parkingPlaces);
	}
	
	private Collection<ParkingPlace> createParkingPlacesPerLevelAndVehicleType(ParkingLevel level, 
			VehicleType type, 
			int numberOfPlaces) {
		Collection<ParkingPlace> places = new HashSet<>();
		
		for (int i = 0; i < numberOfPlaces; i++) {
			ParkingPlace place = new ParkingPlace();
			
			place.setLevel(level);
			place.setType(type);
			place.setNumber(level.getLevel() + "-" + type.name().charAt(0) + "-" + (i + 1));
			places.add(place);
		}
		
		return places;
	}
}
