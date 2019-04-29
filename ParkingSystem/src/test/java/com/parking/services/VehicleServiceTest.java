package com.parking.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.parking.models.Vehicle;
import com.parking.repositories.VehicleRepository;
import com.parking.resources.VehicleType;

@RunWith(SpringRunner.class)
@SpringBootTest 
public class VehicleServiceTest {
	private static final String VEHICLE_NUMBER = "AA-2222-C";
	private static final VehicleType VEHICLE_TYPE = VehicleType.CAR;
	
	@Mock
	VehicleRepository vehicleRepository;
	
	@InjectMocks
	VehicleServiceImpl vehicleService;
	
	@Test
	public void testCreate() {
		Vehicle vehicle = createVehicle();
		
		when(vehicleRepository.save(any(Vehicle.class)))
		.thenReturn(vehicle);
		
		assertEquals(vehicle, 
				vehicleService.create(VEHICLE_NUMBER, VEHICLE_TYPE));
	}
	
	@Test
	public void testFindVehicleByNumber() {
		Vehicle vehicle = createVehicle();
		
		when(vehicleRepository.findByVehicleNumber(VEHICLE_NUMBER))
		.thenReturn(vehicle);
		
		assertEquals(vehicle, 
				vehicleService.
				findByVehicleNumber(VEHICLE_NUMBER).get());
	}
	
	@Test
	public void tesUpdateVehicle() {
		Vehicle vehicle = createVehicle();
		vehicleService.updateVehicle(vehicle);
		
		verify(vehicleRepository).save(vehicle);
	}

	private Vehicle createVehicle() {
		Vehicle vehicle = new Vehicle();
		vehicle.setVehicleNumber(VEHICLE_NUMBER);
		vehicle.setVehicleType(VEHICLE_TYPE);
		
		return vehicle;
	}
}
