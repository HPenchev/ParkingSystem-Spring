package com.parking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parking.models.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}
