package eu.pontsystems.carpool.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="CARS")

public class Car implements Serializable{
	
    private static final long serialVersionUID = 1L;
	
    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Long id;
    
    @Column(name="name")
	private String name;
    
    @Column(name="emptyplaces")
	private int emptyPlaces;
    
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "car")
	@JsonIgnore
    private List<MeetingPoint> meetingPoints;
	
    public Car() {
    	super();
    }

	public Car(Long id, String name, int emptyPlaces, List<MeetingPoint> meetingPoints) {
		super();
		this.id = id;
		this.name = name;
		this.emptyPlaces = emptyPlaces;
		this.meetingPoints = meetingPoints;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEmptyPlaces() {
		return emptyPlaces;
	}

	public void setEmptyPlaces(int emptyPlaces) {
		this.emptyPlaces = emptyPlaces;
	}
	
	public List<MeetingPoint> getMeetingPoints() {
		return meetingPoints;
	}

	public void setMeetingPoints(List<MeetingPoint> meetingPoints) {
		this.meetingPoints = meetingPoints;
	}
	
	
}