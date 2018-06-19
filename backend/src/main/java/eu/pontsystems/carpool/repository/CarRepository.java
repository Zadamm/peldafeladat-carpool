package eu.pontsystems.carpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import eu.pontsystems.carpool.model.Car;

public interface CarRepository extends JpaRepository<Car, Long> {	
	
	@Query("select c from Car c where c.name = :cname")
	Car findByName(@Param("cname") String cname);
}

