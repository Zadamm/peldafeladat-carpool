package eu.pontsystems.carpool.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.http.MediaType;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import eu.pontsystems.carpool.model.Car;
import eu.pontsystems.carpool.model.MeetingPoint;
import eu.pontsystems.carpool.model.Passenger;
import eu.pontsystems.carpool.service.CarService;
import eu.pontsystems.carpool.service.MeetingPointService;
import eu.pontsystems.carpool.service.PassengerService;

@RunWith(SpringRunner.class)
@WebMvcTest(BackendController.class)
public class BackendContorllerTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@Autowired
	private MockMvc mvc;
	 
	@MockBean
	private PassengerService pService;

	@MockBean
	private CarService cService;	

	@MockBean
	private MeetingPointService mpService;
	
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
	
	@Before
	public void setUp() {
		Car c1 = new Car("John");  
        c1.setId(1L);
        
        Passenger p1 = new Passenger("Adam");
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
        
	}
	
	@Test
	public void givenValidRouteInfo_whenSaveRoute_thenSavesRoute() throws IOException, Exception {
		Car c1 = new Car("John");  
        c1.setId(1L);
        c1.setEmptyPlaces(3);
        
        Passenger p1 = new Passenger("Adam");
        p1.setId(1);
        Set<Passenger> ps = new HashSet<Passenger>();
        ps.add(p1);
        
        MeetingPoint mp1 = new MeetingPoint();
        mp1.setId(1);
        mp1.setPlace("valahol");
        mp1.setTime(new Date());
        mp1.setPassengers(ps);
        
        List<MeetingPoint> mpl = new ArrayList<MeetingPoint>();
        mpl.add(mp1);
        
        given(cService.getCarById(c1.getId())).willReturn(c1);
        
        mvc.perform(post("/api/routes")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(mpl))                
                .param("name", c1.getName())
                .param("emptyPlaces", String.valueOf(c1.getEmptyPlaces()))
                .param("carID", String.valueOf(c1.getId()))
                
        )
        		.andExpect(status().isOk());
        verify(cService).save(cService.getCarById(c1.getId()));
	}
	
	@Test
	public void givenValidCarID_whenGetCarById_thenReturnJsonObject() throws Exception {
		Car c1 = new Car("John");  
        c1.setId(1L);
        
        given(cService.getCarById(1L)).willReturn(c1);
        
		mvc.perform(get("/api/cars/1")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$.name", is(c1.getName())));
	}
	
	@Test
	public void givenPassenger_whenGetAllPassengers_thenReturnJsonArray()
	  throws Exception {
		
        Passenger p1 = new Passenger("Adam");
		
	    List<Passenger> pl = Arrays.asList(p1);
	 
	    given(pService.getAllPassenger()).willReturn(pl);
	 
	    mvc.perform(get("/api/passengers")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$", hasSize(1)))
	      .andExpect(jsonPath("$[0].name", is(p1.getName())));
	}
	
	@Test
	public void givenCar_whenGetAllCar_thenReturnJsonArray() throws Exception {
		Car c1 = new Car("John"); 
	    List<Car> cl = Arrays.asList(c1);
	    
	    given(cService.getAllCar()).willReturn(cl);
	    
	    mvc.perform(get("/api/cars")
	  	      .contentType(MediaType.APPLICATION_JSON))
	  	      .andExpect(status().isOk())
	  	      .andExpect(jsonPath("$", hasSize(1)))
	  	      .andExpect(jsonPath("$[0].name", is(c1.getName())));
	}
}
