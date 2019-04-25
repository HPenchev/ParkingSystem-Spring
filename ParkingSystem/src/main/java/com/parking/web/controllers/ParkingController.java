package com.parking.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parking.models.ParkingLevel;
import com.parking.services.ParkingService;

@RestController
@RequestMapping("parking")
public class ParkingController {
	@Autowired
	ParkingService parkingService;

	@RequestMapping(value = "/create",
			method = RequestMethod.POST,
	          produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
	          consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Void> create(@RequestParam(value = "levels", required = true) int numberOfLevels,  
			@RequestParam(value = "cars", required = true) int numberOfCars, 
			@RequestParam(value = "busses", required = true) int numberOfBusses,
			@RequestParam(value = "motorcycles", required = true) int numberOfLMotorcycles,
			@RequestParam(value = "entrances", required = true) int numberOfEntrances,
			@RequestParam(value = "exits", required = true) int numberOfExits) {
		
		parkingService.createParking(numberOfLevels,
				numberOfCars, 
				numberOfBusses, 
				numberOfLMotorcycles, 
				numberOfEntrances, 
				numberOfExits);
		
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

}
