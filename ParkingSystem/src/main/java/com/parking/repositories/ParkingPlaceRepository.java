package com.parking.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.parking.models.ParkingPlace;
import com.parking.resources.VehicleType;

public interface ParkingPlaceRepository extends JpaRepository<ParkingPlace, Long> {
	public ParkingPlace findOneByNumber(String number);
	
	public Collection<ParkingPlace> findAllByType(VehicleType vehicleType);
}
