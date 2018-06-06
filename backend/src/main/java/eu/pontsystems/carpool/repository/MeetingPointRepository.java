package eu.pontsystems.carpool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.pontsystems.carpool.model.MeetingPoint;

public interface MeetingPointRepository extends JpaRepository<MeetingPoint, Integer>{

}
