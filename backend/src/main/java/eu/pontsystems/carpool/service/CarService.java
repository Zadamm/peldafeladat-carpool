package eu.pontsystems.carpool.service;

import java.util.List;

import eu.pontsystems.carpool.model.Car;

public interface CarService {
	//getting the car by it's ID
	public Car getCarById(Long id);
	
	//get all the Cars in a list
	public List<Car> getAllCar();
	
	//saves a car and returns it's id
	public Long save(Car c);
	
	//deletes one car by it's id, returns it's success
	public boolean deleteById(Long id);
}
