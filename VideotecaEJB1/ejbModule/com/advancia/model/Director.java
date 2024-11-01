package com.advancia.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "director")
public class Director {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "lastname")
	private String lastname;

	@OneToMany(mappedBy = "director", fetch = FetchType.EAGER)
	private List<DVD> listDVD;

	public Director() {
		super();
	}

	public Director(String name, String lastname, Date birthDate) {
		super();
		this.name = name;
		this.lastname = lastname;
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

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public List<DVD> getListDVD() {
		return listDVD;
	}

	public void setListDVD(List<DVD> listDVD) {
		this.listDVD = listDVD;
	}

}
