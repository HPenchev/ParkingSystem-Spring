package com.parking.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.parking.resources.VehicleType;

@Entity
@Table(name="vehicles")
public class Vehicle {
	@Id
	@Column
	private long id;
	
	@Column(name = "vehicle_type")
	private VehicleType vehicleType;
	
	@Column(name="vehicle_number", unique=true, nullable=false)
	private String vehicleNumber;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_place_id")
	private ParkingPlace parkingPlace;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public ParkingPlace getParkingPlace() {
		return parkingPlace;
	}

	public void setParkingPlace(ParkingPlace parkingPlace) {
		this.parkingPlace = parkingPlace;
	}
}
