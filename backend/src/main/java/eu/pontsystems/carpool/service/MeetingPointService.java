package eu.pontsystems.carpool.service;

import eu.pontsystems.carpool.model.MeetingPoint;

public interface MeetingPointService {
	public void save(MeetingPoint mp);
	public MeetingPoint getMeetingPointById(Integer id); 
}
