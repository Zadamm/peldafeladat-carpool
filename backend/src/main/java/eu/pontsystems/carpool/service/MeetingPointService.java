package eu.pontsystems.carpool.service;

import eu.pontsystems.carpool.model.MeetingPoint;

public interface MeetingPointService {
	
	//saves one meetingpoint
	public void save(MeetingPoint mp);
	
	//get one metingpoint by it's id
	public MeetingPoint getMeetingPointById(Integer id);	
	
	//remove one meetingpoint
	public void remove(MeetingPoint mp);
}
