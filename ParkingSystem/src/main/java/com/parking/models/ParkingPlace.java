package com.parking.models;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

public class ParkingPlace {
	
	@OneToOne(mappedBy = "parkingPlace", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
	private Vehicle vehicle;
}
