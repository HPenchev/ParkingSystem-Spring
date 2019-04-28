package com.parking.resources;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum VehicleType {
	CAR("car"), 
	BUS("bus"), 
	MOTORCYCLE("motorcycle");
	
	private String vehicleType;
	
	private VehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	@Override
	public String toString() {
		return vehicleType;
	}

	@JsonCreator
	public static VehicleType fromValue(String value) {
		for (VehicleType type : values()) {
			if (type.vehicleType.equalsIgnoreCase(value)) {
				return type;
			}
		}
		throw new IllegalArgumentException(
				"Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
	}
}
