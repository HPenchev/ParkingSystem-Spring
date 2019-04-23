package com.parking.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parking.models.ParkingLevel;
import com.parking.services.TestService;

@RestController
@RequestMapping("test")
public class TestController {
	@Autowired
	TestService test;
	
	@PostMapping(value = "/test",
	          produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	  public ResponseEntity<Object> addPaymentInstrument() {

		ParkingLevel level = new ParkingLevel();
		level.setLevel(1);
		
		test.save(level);
		
	    return new ResponseEntity<Object>(HttpStatus.OK);
	  }
}
