package com.parking.resources.responses;

import com.parking.resources.VehicleType;

public class ParkVehicleResponse {
	private String parkingPlaceNumber;
	private int numberOfVehiclesParked;
	private VehicleType vehicleType;
	private String message;
	
	public String getParkingPlaceNumber() {
		return parkingPlaceNumber;
	}
	public void setParkingPlaceNumber(String parkingPlaceNumber) {
		this.parkingPlaceNumber = parkingPlaceNumber;
	}
	public int getNumberOfVehiclesParked() {
		return numberOfVehiclesParked;
	}
	public void setNumberOfVehiclesParked(int numberOfVehiclesParked) {
		this.numberOfVehiclesParked = numberOfVehiclesParked;
	}
	public VehicleType getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
