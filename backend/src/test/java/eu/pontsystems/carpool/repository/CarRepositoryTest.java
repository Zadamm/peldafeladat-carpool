package eu.pontsystems.carpool.repository;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import eu.pontsystems.carpool.model.Car;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CarRepositoryTest {

	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private CarRepository carRepository;
    
    @Test
    public void getOne_ReturnSameCarWithSameId() {
        // given
        Car c1 = new Car();
        c1.setEmptyPlaces(3);
        c1.setName("Adam");
        entityManager.persist(c1);
        entityManager.flush();
        // when
        Car found = carRepository.getOne(c1.getId());
     
        // then
        assertTrue(found.getName().equals(c1.getName()));
    }
}
