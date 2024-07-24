package de.cwansart.unipoll;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// for some reason compilation fails when this class is just called "Choice"
@Entity
public class UChoice {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	public UChoice() {}
	public UChoice(String name) {
		this.name = name;
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
}
