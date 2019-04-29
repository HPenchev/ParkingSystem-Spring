package com.parking.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

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

@RunWith(SpringRunner.class)
@SpringBootTest 
public class ParkingServiceTests {
	@Mock
	private ParkingLevelRepository parkingLevelRepository;
	@Mock
	private ParkingPlaceRepository parkingPlaceRepository;
	@Mock
	private EntranceRepository entranceRepository;
	@Mock 
	ExitRepository exitRepository;
	
	@Mock
	VehicleService vehicleService;
	
	@InjectMocks
	ParkingServiceImpl parkingService;
	
	@Test
	public void testCreateParking() {
		parkingService.createParking(3, 50, 10, 10, 2, 2);
		
		verify(entranceRepository).saveAll(any());
		verify(exitRepository).saveAll(any());
		verify(parkingLevelRepository).saveAll(any());
		verify(parkingPlaceRepository).saveAll(any());
	}
	
	@Test
	public void testParkVehicle() {
		List<ParkingPlace> parkingPlaces = generateCarPlaces();
		parkingPlaces.get(0).setVehicle(new Vehicle());
		parkingPlaces.get(1).setVehicle(new Vehicle());
		
		when(parkingPlaceRepository.findAllByType(VehicleType.CAR)).thenReturn(parkingPlaces);
		
		ResponseEntity<ParkVehicleResponse> response = 
				parkingService.parkVehicle("test", VehicleType.CAR);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		ParkVehicleResponse responseBody = response.getBody();
		assertEquals(3, responseBody.getNumberOfVehiclesParked());
		assertEquals("1-C-3", responseBody.getParkingPlaceNumber());
		verify(parkingPlaceRepository).save(parkingPlaces.get(2));
	}
	
	@Test
	public void testParkVehicleNoPlaces() {
		when(parkingPlaceRepository
				.findAllByType(VehicleType.CAR))
		.thenReturn(new ArrayList<ParkingPlace>());
		
		ResponseEntity<ParkVehicleResponse> response = 
				parkingService.parkVehicle("test", VehicleType.CAR);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		
		assertEquals("No parking places available for a vehicle type", 
				response.getBody().getMessage());
	}
	
	@Test
	public void testQuit() {
		Vehicle vehicle = new Vehicle();
		String vehicleNumber = "AA-9999-C";
		vehicle.setVehicleNumber(vehicleNumber);
		
		ParkingPlace parkingPlace = generateCarPlaces().get(0);
		parkingPlace.setVehicle(vehicle);
		
		when(parkingPlaceRepository.findOneByNumber("1-C-3")).thenReturn(parkingPlace);
		
		ResponseEntity<String> response = parkingService.quit("1-C-3");
		
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody(), vehicleNumber);
		assertNull(parkingPlace.getVehicle());
		verify(parkingPlaceRepository).save(parkingPlace);
	}
	
	@Test
	public void testList() {;
		List<ParkingLevel> levels = createLevels(3);
		
		List<ParkingPlace> parkingPlaces = createParkingPlaces(levels, 10, 3, 3);
		int placesTaken = 0;
		for (int i = 0; i < parkingPlaces.size(); i++) {
			ParkingPlace parkingPlace = parkingPlaces.get(i);
			if (parkingPlace.getLevel().equals(levels.get(0)) && 
					parkingPlace.getType() == VehicleType.CAR) {
				parkingPlace.setVehicle(new Vehicle());
				placesTaken++;
			}
			
			if (placesTaken == 3) {
				break;
			}
		}
		
		when(parkingLevelRepository.findAll()).thenReturn(levels);
		when(parkingPlaceRepository.findAll()).thenReturn(parkingPlaces);
		 
		Map<Integer, Map<VehicleType, ListOfParkingPlacesPerAvailability>> response = 
				parkingService.list();
		
		assertEquals(7,
				response
				.get(0)
				.get(VehicleType.CAR)
				.getAvailableParkingPlaces()
				.size());
		
		assertEquals(3,
				response
				.get(0)
				.get(VehicleType.CAR)
				.getUnavailableParkingPlaces()
				.size());
	}
	
	private List<ParkingPlace> generateCarPlaces() {
		ParkingPlace carPlace1 = new ParkingPlace();
		carPlace1.setNumber("1-C-1");
		carPlace1.setType(VehicleType.CAR);
		ParkingPlace carPlace2 = new ParkingPlace();
		carPlace2.setNumber("1-C-2");
		carPlace2.setType(VehicleType.CAR);
		ParkingPlace carPlace3 = new ParkingPlace();
		carPlace3.setNumber("1-C-3");
		carPlace3.setType(VehicleType.CAR);
		
		return new ArrayList<ParkingPlace>() {{add(carPlace1);
		add(carPlace2);
		add(carPlace3); 
		}};
	}
	
	private List<ParkingLevel> createLevels(int numberOfLevels) {
		List<ParkingLevel> levels = new ArrayList<>();
		
		for (int i = 0; i < numberOfLevels; i++ ) {
			ParkingLevel level = new ParkingLevel();
			level.setLevel(i);
			levels.add(level);
		}
		
		return levels;
	}
	
	private List<ParkingPlace> createParkingPlaces(List<ParkingLevel> levels, 
			int carPlacesPerLevel, 
			int busPlacesPerLevel,
			int motorcyclePlacesPerLevel) {
		List<ParkingPlace> parkingPlaces = new ArrayList<>();
		
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
		
		return parkingPlaces;
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
}
