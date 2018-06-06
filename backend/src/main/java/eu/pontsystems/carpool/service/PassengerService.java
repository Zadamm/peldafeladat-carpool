package eu.pontsystems.carpool.service;

import eu.pontsystems.carpool.model.Passenger;

import java.util.List;

import eu.pontsystems.carpool.model.MeetingPoint;

public interface PassengerService {
	
	//saves a passenger to a car
	public void save(Passenger p);
	
	//returns a Passenger object by an ID
	public Passenger getPassengerById(Integer id);
	
	//return a Passenger object by a name string
	public Passenger findPassengerByName(String name);
	
	//return all of the meetingpoint of one person by their id
	public List<MeetingPoint> getAllMeetingPointsById(Integer id);
}
