package com.advancia.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

class GenreTest {

	 private Genre genre;
	
	@BeforeEach
    void setUp() {
    	MockitoAnnotations.openMocks(this);
        genre = new Genre();
    }

	@Test
	void testSetAndGetId() {
		Long id = 1L;
		genre.setId(id);
		assertEquals(id, genre.getId());
	}

    @Test
	void testSetAndGetName() {
		String name = "HORROR";
		genre.setName(name);
		assertEquals(name, genre.getName());
	}
    
    @Test
	void testConstructorWithParameters() {
		String name = "HORROR";

		Genre genreWithParams = new Genre(name);
		assertEquals(name, genreWithParams.getName());
	}
}
