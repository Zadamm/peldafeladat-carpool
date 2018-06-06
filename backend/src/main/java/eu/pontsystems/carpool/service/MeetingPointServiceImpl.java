package eu.pontsystems.carpool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.pontsystems.carpool.controller.BackendController;
import eu.pontsystems.carpool.model.MeetingPoint;
import eu.pontsystems.carpool.model.Passenger;
import eu.pontsystems.carpool.repository.MeetingPointRepository;

@Service
public class MeetingPointServiceImpl implements MeetingPointService{
		
    private static final Logger LOG = LoggerFactory.getLogger(BackendController.class);
	
	@Autowired
	private MeetingPointRepository mpRepository;
	
	@Override
	public void save(MeetingPoint mp) {
		
		mpRepository.save(mp);
		
	}

	@Override
	public MeetingPoint getMeetingPointById(Integer id) {
		MeetingPoint mp = mpRepository.getOne(id);
		LOG.info("mpService-be kapott adatok: ");
		Set<Passenger> pSet = mp.getPassengers();
		if(pSet.isEmpty()) {
			LOG.info("ures a PassengerSet");
		} else {
	        for(Passenger p : pSet) {
	        	LOG.info("Utasnev: " + p.getName());
	        }
		}
		return mp;
	}

	@Override
	public void remove(MeetingPoint mp) {
		mpRepository.deleteById(mp.getId());		
	}	
	
}
