package com.advancia.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

class DirectorTest {

	private Director director;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		director = new Director();
	}

	@Test
	void testSetAndGetId() {
		Long id = 1L;
		director.setId(id);
		assertEquals(id, director.getId());
	}

	@Test
	void testSetAndGetName() {
		String name = "Quentin";
		director.setName(name);
		assertEquals(name, director.getName());
	}
	
	@Test
	void testSetAndGetLastname() {
		String lastname = "Tarantino";
		director.setLastname(lastname);
		assertEquals(lastname, director.getLastname());
	}

	@Test
	void testConstructorWithParameters() {
		String name = "Quentin";
		String lastname = "Tarantino";

		Director directorWithParams = new Director(name,lastname);
		assertEquals(name, directorWithParams.getName());
		assertEquals(lastname, directorWithParams.getLastname());
	}

}
