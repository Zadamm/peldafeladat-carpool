package eu.pontsystems.carpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.pontsystems.carpool.model.Car;

public interface CarRepository extends JpaRepository<Car, Long> {	
}

