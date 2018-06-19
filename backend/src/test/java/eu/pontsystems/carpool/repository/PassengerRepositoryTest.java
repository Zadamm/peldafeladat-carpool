package eu.pontsystems.carpool.repository;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import eu.pontsystems.carpool.model.Car;
import eu.pontsystems.carpool.model.MeetingPoint;
import eu.pontsystems.carpool.model.Passenger;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PassengerRepositoryTest {
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private PassengerRepository pRepository;
    
    @Test
    public void findByName_ReturnSamePassenger() {
        // given
        Passenger p = new Passenger();
        p.setName("Adam");
        entityManager.persist(p);
        entityManager.flush();
        // when
        Passenger found = pRepository.findByName(p.getName());
     
        // then
        assertTrue(found.getId().equals(p.getId()));
    }
    
    @Test
    public void getAllMeetingPointById_GivenOneMeetingPoint_ReturnSameMeetingPoint() {
    	
        // given
    	//creating a Passenger
        Passenger p = new Passenger();
        p.setName("Adam");
        entityManager.persist(p); //hogyan lesz itt ID????? flush után???
        
        //creating a Car
        Car c = new Car("Peter");
        entityManager.persist(c);
        
        //creating a MeetingPoint
        MeetingPoint mp = new MeetingPoint();
        mp.setPlace("valahol");
        mp.setCar(c);
        mp.setTime(new Date());        
        entityManager.persist(mp);
        entityManager.flush();
        
        //connecting MeetingPoint and Passenger
        Set<MeetingPoint> mpSet = new HashSet<MeetingPoint>();
        mpSet.add(mp);
        p.setMeetingPoints(mpSet);
        entityManager.persist(p);
        entityManager.flush();        
        
        // when
        //finding the MeetingPoint with the corresponding Passenger by the Passenger's ID
        mpSet = pRepository.getAllMeetingPointById(p.getId());
        MeetingPoint fmp = new MeetingPoint();
        for (Iterator<MeetingPoint> it = mpSet.iterator(); it.hasNext(); ) {
        	fmp = it.next();
            if (fmp.getId().equals(mp.getId())) {
            	break;
            }                
        }     
        int foundID = fmp.getId(); //ID found from the database
        int orgID = mp.getId(); //original ID
        
        // then
        assertTrue(foundID == orgID);
    }
}
