package com.advancia.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoInteractions;

class DVDTest {
	
	@Mock
	private User mockUser1;

	@Mock
	private User mockUser2;

    private DVD dvd;
    private Director director;
    private Genre genre;
    private Date rentalDate;

    @BeforeEach
    void setUp() {
    	MockitoAnnotations.openMocks(this);
        director = new Director();
        genre = new Genre();
        rentalDate = new Date();
        dvd = new DVD();
    }

    void testAddMockedUserToListUser() {
		assertTrue(dvd.getUsers().isEmpty());

		dvd.getUsers().add(mockUser1);

		assertEquals(1, dvd.getUsers().size());
		assertTrue(dvd.getUsers().contains(mockUser1));
	}

	@Test
	void testSetAndGetMockedListUser() {
		List<User> mockUserList = new ArrayList<>();
		mockUserList.add(mockUser1);
		mockUserList.add(mockUser2);

		dvd.setUsers(mockUserList);

		assertEquals(mockUserList, dvd.getUsers());
	}

	@Test
	void testInteractionWithMockUser() {
		dvd.getUsers().add(mockUser1);
		verifyNoInteractions(mockUser1);
	}
    
    @Test
	void testSetAndGetId() {
		Long id = 1L;
		dvd.setId(id);
		assertEquals(id, dvd.getId());
	}


    @Test
	void testSetAndGetName() {
		String name = "Troy";
		dvd.setName(name);
		assertEquals(name, dvd.getName());
	}
    
    @Test
    void testSetAndGetRentalDate() {
        Date newRentalDate = new Date(System.currentTimeMillis() + 86400000); // un giorno dopo
        dvd.setRentalDate(newRentalDate);
        assertEquals(newRentalDate, dvd.getRentalDate());
    }

    @Test
    void testSetAndGetDirector() {
        dvd.setDirector(director);
        assertEquals(director, dvd.getDirector());
    }

    @Test
    void testSetAndGetGenre() {
        dvd.setGenre(genre);
        assertEquals(genre, dvd.getGenre());
    }
    
    @Test
	void testSetAndGetListUser() {
		User user1 = new User();
		user1.setId(1L);
		User user2 = new User();
		user2.setId(2L);

		List<User> userList = new ArrayList<>();
		userList.add(user1);
		userList.add(user2);

		dvd.setUsers(userList);
		assertEquals(userList, dvd.getUsers());
	}

	@Test
	void testAddUserToListUser() {
		User user = new User();
		user.setId(3L);

		assertTrue(dvd.getUsers().isEmpty());
		dvd.getUsers().add(user);

		assertEquals(1, dvd.getUsers().size());
		assertTrue(dvd.getUsers().contains(user));
	}

	@Test
	void testConstructorWithParameters() {
		String name = "testUser";

		DVD dvdWithParams = new DVD(name,director,genre,rentalDate);
		assertEquals(name, dvdWithParams.getName());
		assertEquals(director, dvdWithParams.getDirector());
		assertEquals(genre, dvdWithParams.getGenre());
		assertEquals(rentalDate, dvdWithParams.getRentalDate());
		assertTrue(dvdWithParams.getUsers().isEmpty()); // la lista Users deve essere inizializzata (anche nel costruttore)
	}
    
}
