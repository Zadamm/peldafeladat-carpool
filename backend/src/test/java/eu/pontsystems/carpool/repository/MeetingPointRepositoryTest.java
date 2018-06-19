package eu.pontsystems.carpool.repository;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import eu.pontsystems.carpool.model.Car;
import eu.pontsystems.carpool.model.MeetingPoint;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MeetingPointRepositoryTest {

	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private MeetingPointRepository mpRepository;
    
    @Test
    public void getOne_ReturnSameMeetingPointWithSameId() {
        // given
    	// creating the Car
        Car c = new Car("Adam");
        entityManager.persist(c);
        
        // creating the MeetingPoint
        MeetingPoint mp = new MeetingPoint();
        mp.setCar(c);
        mp.setPlace("Valahol");
        mp.setTime(new Date());
        entityManager.persist(mp);
        entityManager.flush();
        
        // when
        MeetingPoint found = mpRepository.getOne(mp.getId());
        
        // then
        assertTrue(mp.getId().equals(found.getId()));
    }
	
}
