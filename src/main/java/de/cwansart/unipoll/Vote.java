package de.cwansart.unipoll;

import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Vote {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToAny
	private List<UChoice> choices;
	
	@ManyToOne
	private Poll poll;
	
	private String userId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public List<UChoice> getChoices() {
		return choices;
	}
	public void setChoices(List<UChoice> choices) {
		this.choices = choices;
	}
	
	public Poll getPoll() {
		return poll;
	}
	public void setPoll(Poll poll) {
		this.poll = poll;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
