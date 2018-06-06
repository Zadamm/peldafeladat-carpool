package eu.pontsystems.carpool.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.pontsystems.carpool.model.MeetingPoint;
import eu.pontsystems.carpool.model.Passenger;
import eu.pontsystems.carpool.repository.PassengerRepository;

@Service
public class PassengerServiceImpl implements PassengerService{
	

	@Autowired
	private PassengerRepository passengerRepository;

	@Override
	public void save(Passenger p) {
		// TODO Auto-generated method stub
		passengerRepository.save(p);
	}
	
	@Override
	public Passenger getPassengerById(Integer id) {
		return passengerRepository.getOne(id);
	}
	
	@Override
	public Passenger findPassengerByName(String name) {
		return passengerRepository.findByName(name);
	}

	@Override
	public List<MeetingPoint> getAllMeetingPointsById(Integer id) {
		return passengerRepository.getAllMeetingPointById(id);
	}

}
