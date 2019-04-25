package com.parking.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.parking.resources.VehicleType;

@Entity
@Table(name="parking_places")
public class ParkingPlace {
	@Id
	@Column
	private long id;
	
	@Column(unique = true, nullable = false)
	private String number;
	
	@Column
	private VehicleType type;
	
	@OneToOne(mappedBy = "parkingPlace", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
	private Vehicle vehicle;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id")
	private ParkingLevel level;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public VehicleType getType() {
		return type;
	}

	public void setType(VehicleType type) {
		this.type = type;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public ParkingLevel getLevel() {
		return level;
	}

	public void setLevel(ParkingLevel level) {
		this.level = level;
	}
}
