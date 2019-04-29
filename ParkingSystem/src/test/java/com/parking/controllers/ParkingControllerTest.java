package com.parking.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.resources.VehicleType;
import com.parking.resources.requests.CreateParkingRequest;
import com.parking.resources.requests.ParkVehicleRequest;
import com.parking.resources.requests.QuitParkingRequest;
import com.parking.resources.responses.ParkVehicleResponse;
import com.parking.services.ParkingService;
import com.parking.web.controllers.ParkingController;

@RunWith(SpringRunner.class)
@SpringBootTest 
public class ParkingControllerTest {
	private static final String BASE_URL = "/parking";
	public static final MediaType APPLICATION_JSON_UTF8 = 
			new MediaType(MediaType.APPLICATION_JSON.getType(), 
					MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@MockBean
	ParkingService parkingService;

	@InjectMocks
	ParkingController parkingController;

	@Autowired
	private WebApplicationContext webApplicationContext;
	MockMvc mockMvc;
	
	@Before
    public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
	
	@Test
	public void testCreate() throws Exception {
		CreateParkingRequest request = new CreateParkingRequest();
		request.setLevels(3);
		request.setEntrances(2);
		request.setExits(2);
		request.setCars(50);
		request.setBusses(10);
		request.setMotorcycles(2);
		
		this.mockMvc.
		perform(post(BASE_URL + "/create")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(new ObjectMapper().writeValueAsString(request))
				)
		.andExpect(status().isCreated());
	}
	
	@Test
	public void testPark() throws Exception {
		ResponseEntity<ParkVehicleResponse> response = 
				new ResponseEntity<>(new ParkVehicleResponse(), HttpStatus.OK);
		
		String vehicleNumber = "AA-FFFF-99";
		VehicleType vehicleType = VehicleType.CAR;
		
		ParkVehicleRequest request = new ParkVehicleRequest();
		request.setVehicleNumber(vehicleNumber);
		request.setVehicleType(vehicleType);
		
		when(parkingService.parkVehicle(vehicleNumber, vehicleType)).thenReturn(response);
		
		this.mockMvc.
		perform(post(BASE_URL + "/park")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(new ObjectMapper().writeValueAsString(request))
				)
		.andExpect(status().isOk());
	}
	
	@Test
	public void testQuit() throws Exception {
		String vehicleNumber = "AA-FFFF-99";
		ResponseEntity<String> response = 
				new ResponseEntity<>(vehicleNumber, HttpStatus.OK);
		
		String parkingPlaceNumber = "1-C-1";
		QuitParkingRequest request = new QuitParkingRequest();
		request.setParkingPlaceNumber(parkingPlaceNumber);
		
		when(parkingService.quit(parkingPlaceNumber)).thenReturn(response);
		
		this.mockMvc.
		perform(post(BASE_URL + "/quit")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(new ObjectMapper().writeValueAsString(request))
				)
		.andExpect(status().isOk())
		.andExpect(content().string(containsString(vehicleNumber)));
	}
	
	@Test
	public void testList() throws Exception {
		this.mockMvc.
		perform(get(BASE_URL + "/list"))
		.andExpect(status().isOk());
	}
}
