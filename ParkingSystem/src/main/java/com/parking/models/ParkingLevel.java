package com.parking.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="parking_levels")
public class ParkingLevel {
	@Id
	@Column
	private long id;
	
	@Column
	private int level;

    @OneToMany(
        mappedBy = "level",
        cascade = CascadeType.ALL
    )
	private Set<ParkingPlace> parkingPlaces;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Set<ParkingPlace> getParkingPlaces() {
		return parkingPlaces;
	}

	public void setParkingPlaces(Set<ParkingPlace> parkingPlaces) {
		this.parkingPlaces = parkingPlaces;
	}
}
