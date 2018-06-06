package eu.pontsystems.carpool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import eu.pontsystems.carpool.model.Passenger;
import eu.pontsystems.carpool.model.MeetingPoint;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {	
	
	@Query("select p from Passenger p where p.name = :pname")
	Passenger findByName(@Param("pname") String pname);
	
	@Query("select p.meetingPoints from Passenger p where p.id = :pid")	
	List<MeetingPoint> getAllMeetingPointById(@Param("pid") Integer id);
}


