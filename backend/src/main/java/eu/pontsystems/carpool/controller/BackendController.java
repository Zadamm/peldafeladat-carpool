package eu.pontsystems.carpool.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import eu.pontsystems.carpool.model.*;
import eu.pontsystems.carpool.service.*;



@RestController()
@RequestMapping("/api")
public class BackendController {

    private static final Logger LOG = LoggerFactory.getLogger(BackendController.class);

    public static final String HELLO_TEXT = "Hello from Spring Boot Backend!";
    
    @Autowired
    private CarService carService;

    @Autowired
    private PassengerService passengerService;
    
    @Autowired
    private MeetingPointService mpService;

    @RequestMapping(path = "/hello")
    public @ResponseBody String sayHello() {
        LOG.info("GET called on /hello resource");
        return HELLO_TEXT;
    }
        
    @GetMapping(path="/cars/{id}")
    public @ResponseBody Car getCarById(@PathVariable("id") Long id) {
        LOG.info("Reading car with id " + id + " from database.");
        Car c = carService.getCarById(id);
        LOG.info(c.toString());
        return c;
    }
    
    @GetMapping(path="/cars/{id}/meetingpoints")
    public @ResponseBody List<MeetingPoint> getCarMeetingPointsById(@PathVariable("id") Long id) {
        LOG.info("Reading car's meetingpoints with id " + id + " from database.");
        Car c = carService.getCarById(id);
        List<MeetingPoint> mpList = c.getMeetingPoints();
        return mpList;
    }
    
    @GetMapping(path="/meetingpoints/{id}/passengers")
    public @ResponseBody List<Passenger> getPassengersByMeetingPointsId(@PathVariable("id") Integer id) {
        LOG.info("Reading car's meetingpoints with id " + id + " from database.");
        MeetingPoint mp = mpService.getMeetingPointById(id);
        List<Passenger> pList = mp.getPassengers();
        return pList;
    }
    
    @GetMapping(path="/cars")
    public @ResponseBody List<Car> getAllCar() {
    	LOG.info("BackendController meghívódik");
    	List<Car> carList = carService.getAllCar();
    	LOG.info("Egy car-okhoz tartozó meetingpoint nevek:");
    	for(Car c : carList) {
    		LOG.info("Nev: " + c.getName());
    		List<MeetingPoint> mpl = c.getMeetingPoints();
    		for(MeetingPoint mp : mpl) {
    			LOG.info(mp.getPlace());
    		}
    	}
        return carList;
    }
    
    @PostMapping(path="/routes")
    public void save(@RequestBody List<MeetingPoint> mpList, @RequestParam String name, @RequestParam int emptyPlaces) {
    	Car car = new Car();
    	car.setName(name);
    	car.setEmptyPlaces(emptyPlaces);
        car.setId(carService.save(car)); //saves the car and returns it's ID to modify the local Car variable	
    	if(mpList != null) {
	    	for (MeetingPoint mp : mpList) {
	    		LOG.info(mp.getPlace());	    		
	    		mp.setCar(car);
	    	}
	    	car.setMeetingPoints(mpList);
    	}else {
    		LOG.info("ures meetingpoint lista");
    	}
        carService.save(car);
    }
    
    @PostMapping(path="/addpassengers")
    public void addPassenger(@RequestBody Passenger passenger, @RequestParam Integer mpID) {
    	
    	Passenger currentP = passengerService.findPassengerByName(passenger.getName());
    	MeetingPoint currentMP = mpService.getMeetingPointById(mpID);
    	LOG.info("kapott passenger Name alapjan");
    	if(currentP != null) {
    		LOG.info(currentP.toString());
	    	LOG.info("Nev: " + currentP.getName());
	    	LOG.info("ID: " + currentP.getId().toString());
    	}
    	
    	if(currentP != null) {
    		
    		LOG.info("nem null");

    		List<MeetingPoint> allMP = passengerService.getAllMeetingPointsById(currentP.getId());
    		
    		allMP.add(currentMP);
    		
    		currentP.setMeetingPoints(allMP);
    		
			passengerService.save(currentP);
    		
			
    	}else{	
    		LOG.info("meg nincs ilyen passenger"); 
    		
    		List<MeetingPoint> allMP = new ArrayList();

			allMP.add(currentMP);
    		
    		passenger.setMeetingPoints(allMP);
    		
			passengerService.save(passenger);   
    	}    	
    }
}
