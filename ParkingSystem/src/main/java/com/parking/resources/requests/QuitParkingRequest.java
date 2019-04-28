package com.parking.resources.requests;

import javax.validation.constraints.NotNull;

public class QuitParkingRequest {
	@NotNull
	private String parkingPlaceNumber;

	public String getParkingPlaceNumber() {
		return parkingPlaceNumber;
	}

	public void setParkingPlaceNumber(String parkingPlaceNumber) {
		this.parkingPlaceNumber = parkingPlaceNumber;
	}
}
