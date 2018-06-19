package eu.pontsystems.carpool.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import eu.pontsystems.carpool.model.Car;
import eu.pontsystems.carpool.model.MeetingPoint;
import eu.pontsystems.carpool.model.Passenger;
import eu.pontsystems.carpool.repository.CarRepository;

@RunWith(SpringRunner.class)
public class CarServiceTest {

	@TestConfiguration
    static class CarServiceImplTestContextConfiguration {
        @Bean
        public CarService carService() {
            return new CarServiceImpl();
        }
    }
    @Autowired    
    private CarService carService;

    @MockBean
    private CarRepository carRepository;
    
    @MockBean
    private PassengerService pService;
    
    @Before
    public void setUp() {
        Car c1 = new Car("john");  
        c1.setId(1L);
        
        Passenger p1 = new Passenger("adam");
        p1.setId(1);
        Set<Passenger> ps = new HashSet<Passenger>();
        ps.add(p1);
        
        MeetingPoint mp1 = new MeetingPoint();
        mp1.setCar(c1);
        mp1.setId(1);
        mp1.setPlace("valahol");
        mp1.setTime(new Date());
        mp1.setPassengers(ps);
        
        Set<MeetingPoint> mps = new HashSet<MeetingPoint>();
        mps.add(mp1);
        List<MeetingPoint> mpl = new ArrayList<MeetingPoint>();
        mpl.add(mp1);
        
        p1.setMeetingPoints(mps);
        c1.setMeetingPoints(mpl);
        

        // for Test: whenGetCarByIdGivenInvalidID_thenThrowEntityNotFoundException
        Mockito.when(carRepository.getOne(8L)).thenThrow(EntityNotFoundException.class);
        
        // for Test: whenGetCarByIdGivenValidId_thenReturnsNormalCar
        Mockito.when(carRepository.getOne(1L)).thenReturn(c1);				
        
        // for Test: whenGetByNameGivenValidName_thenReturnsNormalCar
        Mockito.when(carRepository.findByName(c1.getName())).thenReturn(c1); 
       
        // for Test: whenGetByNameGivenInvalidName_thenThrowEntityNotFoundException
        Mockito.when(carRepository.findByName("valaki")).thenThrow(EntityNotFoundException.class);
        
        // for Test: whenDeletByIdGivenValidData_thenCarAndMeetingPointsDeleted 
        Mockito.when(pService.getPassengerById(1)).thenReturn(p1);
        Mockito.when(pService.getAllMeetingPointsById(1)).thenReturn(mps);
        
        Mockito.doAnswer((Answer) invocation -> {

            int pid = invocation.getArgument(0);
            int mpid = invocation.getArgument(1);
            
            //finding and removing the meetingPoint
            Passenger p = pService.getPassengerById(pid);
            Set<MeetingPoint> mpSet = p.getMeetingPoints();
    		for(MeetingPoint mp : mpSet) {
    			if(mp.getId() == mpid) {
    				mpSet.remove(mp);
    				break;
    			}
    		}                       
            return null;        	
        }).when(pService).deleteMeetingPointOfPassenger(any(Integer.class), any(Integer.class));
    }
    
    @Test(expected = EntityNotFoundException.class)
    public void whenGetCarByIDGivenInvalidID_thenTrowsEntityNotFoundException() {
    	carService.getCarById(8L);
    }
    
    @Test
    public void whenGetCarByIdGivenValidId_thenReturnsNormalCar() {
    	Car found = carService.getCarById(1L);
    	assertTrue(found.getId().equals(1L));
    }
    
    @Test
    public void whenGetByNameGivenValidName_thenCarShouldBeFound() {
        String name = "john";
        Car found = carService.getByName(name);

        assertEquals(found.getName(), name);
    }
    
    @Test(expected = EntityNotFoundException.class)
    public void whenGetByNameGivenInvalidName_thenThrowEntityNotFoundException() {
    	carService.getByName("valaki");
    }
    
    @Test
    public void whenDeleteByIdGivenNullArgument_thenReturnsFalse() {
    	assertFalse(carService.deleteById(8L));   
    }
    
    @Test
    public void whenDeletByIdGivenValidData_thenCarAndMeetingPointsDeleted() {
    	carService.deleteById(1L);
    	assertTrue(pService.getAllMeetingPointsById(1).isEmpty());
    	verify(carRepository).delete(carRepository.getOne(1L));
    }
    
    
}
