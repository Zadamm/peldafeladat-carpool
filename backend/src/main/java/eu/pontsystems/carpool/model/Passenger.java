package eu.pontsystems.carpool.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="PASSENGERS")

public class Passenger implements Serializable{

    private static final long serialVersionUID = 1L;
    
	@Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Integer id;
	
    @Column(name="name", nullable=false)
	private String name;

    @ManyToMany(cascade =  {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    }) 
    @JoinTable(
            name = "meetingpoints_has_passengers", 
            joinColumns = { @JoinColumn(name = "passengers_id") }, 
            inverseJoinColumns = { @JoinColumn(name = "meetingpoints_id") }
    )
    @JsonIgnore
    private Set<MeetingPoint> meetingPoints;
	
    public Passenger(){
        super();
    }

	public Passenger(Integer id, String name, Set<MeetingPoint> meetingPoints) {
		super();
		this.id = id;
		this.name = name;
		this.meetingPoints = meetingPoints;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<MeetingPoint> getMeetingPoints() {
		return meetingPoints;
	}

	public void setMeetingPoints(Set<MeetingPoint> meetingPoints) {
		this.meetingPoints = meetingPoints;
	}



}
