package model;
import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class ActivityType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5935634989269290056L;

	private String atName;

	@JsonIgnore
	@XmlTransient
	private List<Activity> activities;
	
	public ActivityType() {
		
	}

	public String getAtName() {
		return atName;
	}

	public void setAtName(String atName) {
		this.atName = atName;
	}

	@JsonIgnore
	public List<Activity> getActivities() {
		return activities;
	}

	@JsonProperty
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	
	public void addActivity(Activity activity) {
        this.activities.add(activity);
        if (activity.getType() != this) {
            activity.setType(this);
        }
    }
	
	// Database operations
	// Notice that, for this example, we create and destroy and entityManager on each operation. 
	// How would you change the DAO to not having to create the entity manager every time? 
	



}
