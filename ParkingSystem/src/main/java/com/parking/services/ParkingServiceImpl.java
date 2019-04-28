package com.parking.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.parking.models.Entrance;
import com.parking.models.Exit;
import com.parking.models.ParkingLevel;
import com.parking.models.ParkingPlace;
import com.parking.models.Vehicle;
import com.parking.repositories.EntranceRepository;
import com.parking.repositories.ExitRepository;
import com.parking.repositories.ParkingLevelRepository;
import com.parking.repositories.ParkingPlaceRepository;
import com.parking.resources.VehicleType;
import com.parking.resources.responses.ListOfParkingPlacesPerAvailability;
import com.parking.resources.responses.ParkVehicleResponse;

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
	
	@Autowired
	VehicleService vehicleService;
	
	@Override
	public void createParking(int numberOfLevels,
			int carPlacesPerLevel, 
			int busPlacesPerLevel,
			int motorcyclePlacesPerLevel, 
			int numberOfEntrances, 
			int numberOfExits) {
		createEntrances(numberOfEntrances);
		
		createExits(numberOfExits);

		createParkingPlaces(createLevels(numberOfLevels), 
				carPlacesPerLevel, 
				busPlacesPerLevel, 
				motorcyclePlacesPerLevel);
	}
	
	@Override
	public synchronized ResponseEntity<ParkVehicleResponse> parkVehicle(String vehicleNumber, 
			VehicleType vehicleType) {
		Vehicle vehicle = getVehicle(vehicleNumber, vehicleType);
		
		Collection<ParkingPlace> parkingPlaces = 
				parkingPlaceRepository.findAllByType(vehicleType);
		
		ParkingPlace parkingPlace = 
				parkingPlaces.stream().filter(p -> p.getVehicle() == null).findAny().orElse(null);
		int vehiclesParked = (
				int) parkingPlaces.stream().filter(p -> p.getVehicle() != null).count();
		
		ParkVehicleResponse response = new ParkVehicleResponse();
		response.setVehicleType(vehicleType);
		
		HttpStatus status = null;
		
		if (parkingPlace == null) {
			response.setMessage("No parking places available for a vehicle type");
			status = HttpStatus.UNPROCESSABLE_ENTITY;
		} else {
			parkingPlace.setVehicle(vehicle);
			parkingPlaceRepository.save(parkingPlace);
			String parkingPlaceNumber = parkingPlace.getNumber();
			
			response.setParkingPlaceNumber(parkingPlaceNumber);
			response.setMessage(vehicleType +
					" " + 
					vehicleNumber + 
					" successfully parked on " + 
					parkingPlaceNumber);
			vehiclesParked++;
			status = HttpStatus.OK;
		}
		
		response.setNumberOfVehiclesParked(vehiclesParked);
				
		return new ResponseEntity<ParkVehicleResponse>(response, status);
	}

	@Override
	public ResponseEntity<String> quit(String parkingPlaceNumber) {
		ParkingPlace parkingPlace = 
				parkingPlaceRepository.findOneByNumber(parkingPlaceNumber);
		
		Vehicle vehicle = parkingPlace.getVehicle();
		parkingPlace.setVehicle(null);
		parkingPlaceRepository.save(parkingPlace);
		
		return new ResponseEntity<String>(vehicle.getVehicleNumber(), HttpStatus.OK);
	}
	
	@Override
	public Map<Integer, Map<VehicleType, ListOfParkingPlacesPerAvailability>> list() {
		List<ParkingLevel> parkingLevels = parkingLevelRepository.findAll();
		
		Map<Integer, Map<VehicleType, ListOfParkingPlacesPerAvailability>> response = 
				new HashMap<>();
		
		for (ParkingLevel parkingLevel : parkingLevels) {
			
			Map<VehicleType, ListOfParkingPlacesPerAvailability> availabilitiesPerType = 
					new EnumMap<>(VehicleType.class);
			
			for(VehicleType vehicleType : VehicleType.values()) {
				availabilitiesPerType.put(vehicleType, new ListOfParkingPlacesPerAvailability());
			}
			
			response.put(parkingLevel.getLevel(), 
					availabilitiesPerType);
		}
		
		addParkingPlaces(response);
		
		return response;
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
			levels.add(level);
		}
		
		return parkingLevelRepository.saveAll(levels);
	}

	private void createParkingPlaces(List<ParkingLevel> levels, 
			int carPlacesPerLevel, 
			int busPlacesPerLevel,
			int motorcyclePlacesPerLevel) {
		Collection<ParkingPlace> parkingPlaces = new HashSet<>();
		
		for (ParkingLevel level : levels) {
			parkingPlaces.addAll(
					createParkingPlacesPerLevelAndVehicleType(
							level, 
							VehicleType.CAR, 
							carPlacesPerLevel));
			parkingPlaces.addAll(
					createParkingPlacesPerLevelAndVehicleType(
							level,
							VehicleType.BUS, 
							busPlacesPerLevel));
			parkingPlaces.addAll(
					createParkingPlacesPerLevelAndVehicleType(
							level,
							VehicleType.MOTORCYCLE, 
							motorcyclePlacesPerLevel));
		}
		
		parkingPlaceRepository.saveAll(parkingPlaces);
	}
	
	private Collection<ParkingPlace> createParkingPlacesPerLevelAndVehicleType(
			ParkingLevel level, 
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

	private Vehicle getVehicle(String vehicleNumber, VehicleType vehicleType) {
		Vehicle vehicle = null;
		Optional<Vehicle> vehicleOptional = vehicleService.findByVehicleNumber(vehicleNumber);
		
		if (vehicleOptional.isPresent()) {
			vehicle = vehicleOptional.get();
		} else {
			vehicle = vehicleService.create(vehicleNumber, vehicleType);
		}
		
		return vehicle;
	}
	
	private void addParkingPlaces(
			Map<Integer, Map<VehicleType, ListOfParkingPlacesPerAvailability>> response) {
		Collection<ParkingPlace> parkingPlaces = parkingPlaceRepository.findAll();
		
		for (Entry<Integer, Map<VehicleType, ListOfParkingPlacesPerAvailability>> levelEntry : 
			response.entrySet()) {
			
			Map<VehicleType, ListOfParkingPlacesPerAvailability> vehiclesPerlevel = 
					levelEntry.getValue();
			
			for (Entry<VehicleType, ListOfParkingPlacesPerAvailability> typeEntry : 
				vehiclesPerlevel.entrySet()) {
				ListOfParkingPlacesPerAvailability parkingAvailabilities = typeEntry.getValue();
				
				Collection<ParkingPlace> parkingPlacesPerLevelAndType = parkingPlaces.stream()
						.filter(p -> p.getLevel().getLevel() == levelEntry.getKey() && 
						p.getType() == typeEntry.getKey()).collect(Collectors.toList());
				
				parkingAvailabilities.
				setAvailableParkingPlaces(mapParkingPlaces(parkingPlacesPerLevelAndType, true));
				
				parkingAvailabilities.
				setUnavailableParkingPlaces(mapParkingPlaces(parkingPlacesPerLevelAndType, false));
			}
		}
	}

	private List<String> mapParkingPlaces(Collection<ParkingPlace> parkingPlacesPerLevelAndType, boolean isAvailable) {
		return parkingPlacesPerLevelAndType.stream()
				.filter(p -> (p.getVehicle() == null) == isAvailable)
				.map(p -> p.getNumber())
				.collect(Collectors.toList());
	}
}
