package com.parking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parking.models.ParkingPlace;

public interface ParkingPlaceRepository extends JpaRepository<ParkingPlace, Long> {

}
