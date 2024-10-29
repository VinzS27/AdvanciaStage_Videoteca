package com.advancia.controller;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.advancia.DAO.VideotecaDAOLocal;
import com.advancia.model.User;

class DashboardServletUserTest {

	@InjectMocks
	private DashboardServletUser dashboardServletUser;

	@Mock
	private VideotecaDAOLocal videotecaDaoLocal;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Mock
	private HttpSession session;

	@Mock
	private RequestDispatcher requestDispatcher;

	private User mockUser;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

		mockUser = new User("username", "password");
		mockUser.setId(1L);
	}

	@Test
	void testProcessRequestRentDVD() throws ServletException, IOException {
		when(request.getParameter("actions")).thenReturn("rentDVD");
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("user")).thenReturn(mockUser);
		when(request.getParameter("id_dvd")).thenReturn("100");
		when(request.getRequestDispatcher("/dashboardUser.jsp")).thenReturn(requestDispatcher);

		when(videotecaDaoLocal.getAvailableDVDs()).thenReturn(new ArrayList<>());
		when(videotecaDaoLocal.getDirector()).thenReturn(new ArrayList<>());
		when(videotecaDaoLocal.getGenre()).thenReturn(new ArrayList<>());

		dashboardServletUser.processRequest(request, response);

		verify(videotecaDaoLocal).rentDVD(mockUser.getId(), 100L);
		verify(request).setAttribute(eq("availableDVDs"), anyList()); //eq verifica che il parametro corrisponda al valore specificato.
		verify(request).setAttribute(eq("directors"), anyList()); // anyList() accetta qualsiasi lista come parametro.
		verify(request).setAttribute(eq("genres"), anyList());
		verify(requestDispatcher).forward(request, response);
	}
}
