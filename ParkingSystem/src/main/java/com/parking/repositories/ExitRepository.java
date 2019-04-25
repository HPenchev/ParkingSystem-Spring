package com.parking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parking.models.Exit;

public interface ExitRepository extends JpaRepository<Exit, Long> {

}
