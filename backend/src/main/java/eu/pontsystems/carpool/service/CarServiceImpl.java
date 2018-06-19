package eu.pontsystems.carpool.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.pontsystems.carpool.model.Car;
import eu.pontsystems.carpool.model.MeetingPoint;
import eu.pontsystems.carpool.model.Passenger;
import eu.pontsystems.carpool.repository.CarRepository;

@Service
public class CarServiceImpl implements CarService{
	

    private static final Logger LOG = LoggerFactory.getLogger(CarServiceImpl.class);

	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private PassengerService pService;
	
	@Override
	public Car getCarById(Long id) {
		return carRepository.getOne(id);
	}
	
	@Override
	public Car getByName(String name) {
		return carRepository.findByName(name);
	}
	
	@Override
	public List<Car> getAllCar(){
		return carRepository.findAll();		
	}
	
	@Override
	public Long save(Car c) {
		return carRepository.save(c).getId();
	}
	

	@Override
	public boolean deleteById(Long id) {
		Car c = new Car();
		try {
			c = carRepository.getOne(id);
		} catch(EntityNotFoundException e) {
			LOG.info("Entity not found in the database");
			return false;
		}
		for(MeetingPoint mp : c.getMeetingPoints()) {
			for(Passenger p : mp.getPassengers()) {
				pService.deleteMeetingPointOfPassenger(p.getId(), mp.getId());
			}
		}
		boolean retval = true;
		try {
			carRepository.delete(c);
		} catch(IllegalArgumentException e) {
			LOG.info("Deletition was given null entity");
			retval = false;
			throw e;//?????
		}
		return retval;
		
	}
	
}
