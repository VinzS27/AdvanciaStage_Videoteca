package com.advancia.DTO;

import java.util.List;

import com.advancia.model.DVD;

public class UserDTO {

	private Long id;

	private String username;

	private String password;

	private String role;

	private List<DVD> listDVD;

	public UserDTO() {
		super();
	}

	public UserDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<DVD> getListDVD() {
		return listDVD;
	}

	public void setListDVD(List<DVD> listDVD) {
		this.listDVD = listDVD;
	}

}
