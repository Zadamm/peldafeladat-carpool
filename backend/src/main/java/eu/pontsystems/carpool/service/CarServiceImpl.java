package eu.pontsystems.carpool.service;

import java.util.List;

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
		//System.out.println("carservice impl");
		return carRepository.getOne(id);
		/*
		List<Car> vv = carRepository.findAll();
		System.out.println(vv.size());
		return vv.get(id);
		*/
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
		Car c = carRepository.getOne(id);
		
		for(MeetingPoint mp : c.getMeetingPoints()) {
			for(Passenger p : mp.getPassengers()) {
				pService.deleteMeetingPointOfPassenger(p.getId(), mp.getId());
			}
		}
		
		try {
			carRepository.delete(c);
		} catch(IllegalArgumentException e) {
			return false;
		}
		return true;
	}
	
}
