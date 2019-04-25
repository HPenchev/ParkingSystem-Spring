package com.parking.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="parking_places")
public class ParkingPlace {
	@Id
	@Column
	private long id;
	
	@Column(unique = true, nullable = false)
	private String number;
	
	@OneToOne(mappedBy = "parkingPlace", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
	private Vehicle vehicle;
	
	@Column(name = "parking_level")
	private ParkingLevel level;
}
