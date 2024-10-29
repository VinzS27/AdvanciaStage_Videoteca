package com.advancia.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserTest {

	private User user;

	@Mock
	private DVD mockDVD1;

	@Mock
	private DVD mockDVD2;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		user = new User();
	}

	@Test
	void testAddMockedDVDToListDVD() {
		// Verifica che la lista sia inizialmente vuota
		assertTrue(user.getListDVD().isEmpty());

		user.getListDVD().add(mockDVD1);

		// Verifica che il DVD mockato sia stato aggiunto
		assertEquals(1, user.getListDVD().size());
		assertTrue(user.getListDVD().contains(mockDVD1));
	}

	@Test
	void testSetAndGetMockedListDVD() {
		List<DVD> mockDVDList = new ArrayList<>();
		mockDVDList.add(mockDVD1);
		mockDVDList.add(mockDVD2);

		user.setListDVD(mockDVDList);

		// Verifica che getListDVD restituisce la lista mockata
		assertEquals(mockDVDList, user.getListDVD());
	}

	@Test
	void testInteractionWithMockDVD() {
		user.getListDVD().add(mockDVD1);
		verifyNoInteractions(mockDVD1); // verifica che non ci sia stata alcuna interazione con mockDVD1
	}

	@Test
	void testSetAndGetId() {
		Long id = 1L;
		user.setId(id);
		assertEquals(id, user.getId());
	}

	@Test
	void testSetAndGetUsername() {
		String username = "testUser";
		user.setUsername(username);
		assertEquals(username, user.getUsername());
	}

	@Test
	void testSetAndGetPassword() {
		String password = "password123";
		user.setPassword(password);
		assertEquals(password, user.getPassword());
	}

	@Test
	void testSetAndGetRole() {
		String role = "admin";
		user.setRole(role);
		assertEquals(role, user.getRole());
	}

	@Test
	void testSetAndGetListDVD() {
		DVD dvd1 = new DVD();
		dvd1.setId(1L);
		DVD dvd2 = new DVD();
		dvd2.setId(2L);

		List<DVD> dvdList = new ArrayList<>();
		dvdList.add(dvd1);
		dvdList.add(dvd2);

		user.setListDVD(dvdList);
		assertEquals(dvdList, user.getListDVD());
	}

	@Test
	void testAddDVDToListDVD() {
		DVD dvd = new DVD();
		dvd.setId(3L);

		assertTrue(user.getListDVD().isEmpty());
		user.getListDVD().add(dvd);

		//verifica che è stato aggiunto
		assertEquals(1, user.getListDVD().size());
		assertTrue(user.getListDVD().contains(dvd));
	}

	@Test
	void testConstructorWithParameters() {
		String username = "testUser";
		String password = "password123";

		User userWithParams = new User(username, password);
		assertEquals(username, userWithParams.getUsername());
		assertEquals(password, userWithParams.getPassword());
		assertTrue(userWithParams.getListDVD().isEmpty()); // listDVD deve essere inizializzata (anche nel costruttore)
	}
}
