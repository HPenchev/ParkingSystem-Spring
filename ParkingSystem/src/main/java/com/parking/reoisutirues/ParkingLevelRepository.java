package com.parking.reoisutirues;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parking.models.ParkingLevel;

@Repository
public interface ParkingLevelRepository extends JpaRepository<ParkingLevel, Long> {

}
