package eu.pontsystems.carpool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.pontsystems.carpool.model.Car;
import eu.pontsystems.carpool.model.MeetingPoint;
import eu.pontsystems.carpool.repository.MeetingPointRepository;

@Service
public class MeetingPointServiceImpl implements MeetingPointService{

	
	@Autowired
	private MeetingPointRepository mpRepository;
	
	@Override
	public void save(MeetingPoint mp) {
		
		mpRepository.save(mp);
		
	}

	@Override
	public MeetingPoint getMeetingPointById(Integer id) {
		return mpRepository.getOne(id);
	}
}
