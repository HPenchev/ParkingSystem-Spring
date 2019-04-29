package com.parking.web.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parking.resources.VehicleType;
import com.parking.resources.requests.CreateParkingRequest;
import com.parking.resources.requests.ParkVehicleRequest;
import com.parking.resources.requests.QuitParkingRequest;
import com.parking.resources.responses.ListOfParkingPlacesPerAvailability;
import com.parking.resources.responses.ParkVehicleResponse;
import com.parking.services.ParkingService;

@RestController
@RequestMapping("/parking")
public class ParkingController {
	@Autowired
	ParkingService parkingService;

	@PostMapping(value = "/create",
	          produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
	          consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Void> create(
			@Valid @RequestBody CreateParkingRequest request) {
		parkingService.createParking(request.getLevels(),
				request.getCars(), 
				request.getBusses(), 
				request.getMotorcycles(), 
				request.getEntrances(), 
				request.getExits());
		
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@PostMapping(value = "/park",
	          produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
	          consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ParkVehicleResponse> park(
			@Valid @RequestBody ParkVehicleRequest request) {
		
		return parkingService.parkVehicle(request.getVehicleNumber(), request.getVehicleType());
	}
	
	@PostMapping(value = "/quit",
	          produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
	          consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> park(@Valid
			@RequestBody QuitParkingRequest request) {
		
		return parkingService.quit(request.getParkingPlaceNumber());
	}
	
	@GetMapping(value = "/list",
	          produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<Integer, Map<VehicleType, ListOfParkingPlacesPerAvailability>> list() {
		
		return parkingService.list();
	}
}
