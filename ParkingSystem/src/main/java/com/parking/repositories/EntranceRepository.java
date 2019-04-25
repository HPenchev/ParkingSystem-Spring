package com.parking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parking.models.Entrance;

public interface EntranceRepository extends JpaRepository<Entrance, Long> {

}
