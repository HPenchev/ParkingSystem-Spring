package com.parking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.parking.models.ParkingLevel;
import com.parking.reoisutirues.ParkingLevelRepository;
import com.parking.services.TestService;

@SpringBootApplication
public class ParkingSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(ParkingSystemApplication.class, args);
	}

}
