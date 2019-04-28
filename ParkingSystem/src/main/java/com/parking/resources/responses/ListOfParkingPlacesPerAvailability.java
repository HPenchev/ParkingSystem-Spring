package com.parking.resources.responses;

import java.util.Collection;

public class ListOfParkingPlacesPerAvailability {
	private Collection<String> availableParkingPlaces;
	private Collection<String> unavailableParkingPlaces;
	
	public Collection<String> getAvailableParkingPlaces() {
		return availableParkingPlaces;
	}
	
	public void setAvailableParkingPlaces(Collection<String> availableParkingPlaces) {
		this.availableParkingPlaces = availableParkingPlaces;
	}
	
	public Collection<String> getUnavailableParkingPlaces() {
		return unavailableParkingPlaces;
	}
	
	public void setUnavailableParkingPlaces(Collection<String> unavailableParkingPlaces) {
		this.unavailableParkingPlaces = unavailableParkingPlaces;
	}
}
