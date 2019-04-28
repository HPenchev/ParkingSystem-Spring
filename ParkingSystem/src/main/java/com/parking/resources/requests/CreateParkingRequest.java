package com.parking.resources.requests;

import javax.validation.constraints.NotNull;

public class CreateParkingRequest {
	@NotNull(message = "Levels are mandatory")
	private Integer levels;
	@NotNull(message = "cars are mandatory")
	private Integer cars;
	@NotNull(message = "busses are mandatory")
	private Integer busses;
	@NotNull(message = "motorcycles are mandatory")
	private Integer motorcycles;
	@NotNull(message = "entrances are mandatory")
	private Integer entrances;
	@NotNull(message = "exits are mandatory")
	private Integer exits;
	
	public Integer getLevels() {
		return levels;
	}
	
	public void setLevels(Integer levels) {
		this.levels = levels;
	}
	
	public Integer getCars() {
		return cars;
	}
	
	public void setCars(Integer cars) {
		this.cars = cars;
	}
	
	public Integer getBusses() {
		return busses;
	}
	
	public void setBusses(Integer busses) {
		this.busses = busses;
	}
	
	public Integer getMotorcycles() {
		return motorcycles;
	}
	public void setMotorcycles(Integer motorcycles) {
		this.motorcycles = motorcycles;
	}
	
	public Integer getEntrances() {
		return entrances;
	}
	
	public void setEntrances(Integer entrances) {
		this.entrances = entrances;
	}
	
	public Integer getExits() {
		return exits;
	}
	
	public void setExits(Integer exits) {
		this.exits = exits;
	}
}
