package com.parking.resources.requests;

import javax.validation.constraints.NotNull;

import com.parking.resources.VehicleType;

public class ParkVehicleRequest {
	@NotNull
	private String vehicleNumber;
	@NotNull
	private VehicleType vehicleType;
	
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	
	public VehicleType getVehicleType() {
		return vehicleType;
	}
	
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
}
