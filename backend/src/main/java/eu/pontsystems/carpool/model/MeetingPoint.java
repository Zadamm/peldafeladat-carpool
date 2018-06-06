package eu.pontsystems.carpool.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="MEETINGPOINTS")
 
public class MeetingPoint implements Serializable{

    private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Integer id;

    @Column(name="place", nullable=false)
	private String place;

    @Column(name="time", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date time;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cars_id", nullable = false)
    @JsonIgnore
    private Car car;

    @ManyToMany(cascade =  {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    }, mappedBy = "meetingPoints")
    @JsonIgnore
    private Set<Passenger> passengers;

	public MeetingPoint(Integer id, String place, Date time, Car car, Set<Passenger> passengers) {
		super();
		this.id = id;
		this.place = place;
		this.time = time;
		this.car = car;
		this.passengers = passengers;
	}
	
	public MeetingPoint() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
    
	public Set<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(Set<Passenger> passengers) {
		this.passengers = passengers;
	}

}

