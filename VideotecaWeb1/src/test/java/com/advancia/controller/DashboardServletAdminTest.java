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

class DashboardServletAdminTest {

	@InjectMocks
	private DashboardServletAdmin dashboardServletAdmin;

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
	void testRentDVD() throws ServletException, IOException {
		when(request.getParameter("actions")).thenReturn("rentDVD");
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("user")).thenReturn(mockUser);
		when(request.getParameter("id_dvd")).thenReturn("100");
		when(request.getRequestDispatcher("/dashboardAdmin.jsp")).thenReturn(requestDispatcher);

		when(videotecaDaoLocal.getAllDVDs()).thenReturn(new ArrayList<>());
		when(videotecaDaoLocal.getDirector()).thenReturn(new ArrayList<>());
		when(videotecaDaoLocal.getGenre()).thenReturn(new ArrayList<>());

		dashboardServletAdmin.processRequest(request, response);

		verify(videotecaDaoLocal).rentDVD(mockUser.getId(), 100L);
		verify(request).setAttribute(eq("dvds"), anyList()); //eq verifica che il parametro corrisponda al valore specificato.
		verify(request).setAttribute(eq("directors"), anyList()); // anyList() accetta qualsiasi lista come parametro.
		verify(request).setAttribute(eq("genres"), anyList());
		verify(requestDispatcher).forward(request, response);
	}
	
	@Test
    public void testAddNewDVD() throws ServletException, IOException {
        when(request.getParameter("actions")).thenReturn("addDVD");
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("DVDName")).thenReturn("Matrix");
        when(request.getParameter("id_genre")).thenReturn("2");
        when(request.getParameter("id_director")).thenReturn("1");
        when(request.getRequestDispatcher("/dashboardAdmin.jsp")).thenReturn(requestDispatcher);
        
        when(videotecaDaoLocal.getAllDVDs()).thenReturn(new ArrayList<>());
		when(videotecaDaoLocal.getDirector()).thenReturn(new ArrayList<>());
		when(videotecaDaoLocal.getGenre()).thenReturn(new ArrayList<>());

        dashboardServletAdmin.processRequest(request, response);

        verify(videotecaDaoLocal).addDVD("Matrix", 1L, 2L);
		verify(request).setAttribute(eq("dvds"), anyList()); //eq verifica che il parametro corrisponda al valore specificato.
		verify(request).setAttribute(eq("directors"), anyList()); // anyList() accetta qualsiasi lista come parametro.
		verify(request).setAttribute(eq("genres"), anyList());
		verify(requestDispatcher).forward(request, response);
    }
	
	@Test
    public void testDeleteDVD() throws ServletException, IOException {
        when(request.getParameter("actions")).thenReturn("deleteDVD");
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("id_dvd")).thenReturn("100");
        when(request.getRequestDispatcher("/dashboardAdmin.jsp")).thenReturn(requestDispatcher);
        
        when(videotecaDaoLocal.getAllDVDs()).thenReturn(new ArrayList<>());
		when(videotecaDaoLocal.getDirector()).thenReturn(new ArrayList<>());
		when(videotecaDaoLocal.getGenre()).thenReturn(new ArrayList<>());

        dashboardServletAdmin.processRequest(request, response);

        verify(videotecaDaoLocal).deleteDVD(100L);
		verify(request).setAttribute(eq("dvds"), anyList()); //eq verifica che il parametro corrisponda al valore specificato.
		verify(request).setAttribute(eq("directors"), anyList()); // anyList() accetta qualsiasi lista come parametro.
		verify(request).setAttribute(eq("genres"), anyList());
		verify(requestDispatcher).forward(request, response);
    }
}
