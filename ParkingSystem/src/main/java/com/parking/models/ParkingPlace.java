package com.parking.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.parking.resources.VehicleType;

@Entity
@Table(name="parking_places")
public class ParkingPlace {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private long id;
	
	@Column(unique = true, nullable = false)
	private String number;
	
	@Column
	private VehicleType type;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "parking_places_vehicles", 
      joinColumns = { @JoinColumn(name = "parking_place_id", referencedColumnName = "id") },
      inverseJoinColumns = { @JoinColumn(name = "vehicle_id", referencedColumnName = "id")})
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
