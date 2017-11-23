package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;


@XmlRootElement(name="activity")
@JsonRootName(value="activity")
@XmlAccessorType(XmlAccessType.FIELD)

public class Activity implements Serializable{

	private static final long serialVersionUID = -445012277184098930L;




	@XmlAttribute
	private int idActivity;
	

	private String name;
	

	private String description;
	
	
	private String startdate;
	
	private String place;
	
	
	@XmlTransient
	@JsonIgnore
	private Person person;
	
	
	@XmlElement(name="activity_type")
	@JsonProperty(value="activity_type")
	private ActivityType type;
	
	public Activity() {}
    public Activity(Activity a) {
    	this.idActivity=a.idActivity;
    	this.name=a.name;
    	this.description=a.description;
    	this.place=a.place;
    	this.type=a.type;
    	this.startdate=a.startdate;
    	this.person=a.person;
    }

	public int getIdActivity() {
		return idActivity;
	}

	public void setIdActivity(int idActivity) {
		this.idActivity = idActivity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	@JsonIgnore
	@XmlTransient
	public Person getPerson() {
		return person;
	}
	@JsonProperty
	public void setPerson(Person person) {
		this.person = person;
		/*if(!person.getActivities().contains(this)) {
			person.getActivities().add(this);
		}*/
	}

	public ActivityType getType() {
		return type;
	}

	public void setType(ActivityType type) {
		this.type = type;
		/*if(!type.getActivities().contains(this)) {
			type.getActivities().add(this);
		}*/
	}
	
	// Database operations
	// Notice that, for this example, we create and destroy and entityManager on each operation. 
	// How would you change the DAO to not having to create the entity manager every time? 
	
}
