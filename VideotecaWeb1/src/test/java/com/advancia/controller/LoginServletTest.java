package com.advancia.controller;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.advancia.DAO.VideotecaDAOLocal;
import com.advancia.model.User;

@ExtendWith(MockitoExtension.class) 
class LoginServletTest{

    @InjectMocks
    private LoginServlet loginServlet;

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

        mockUser = new User("user", "pass");
        mockUser.setRole("USER");
    }

    @Test
    void testProcessRequest() throws ServletException, IOException {
        when(request.getParameter("username")).thenReturn("user");
        when(request.getParameter("password")).thenReturn("pass");
        when(request.getSession()).thenReturn(session);

        User mockUser = new User("user", "pass");
        mockUser.setRole("USER");
        when(videotecaDaoLocal.checkUser("user", "pass")).thenReturn(mockUser);

        when(videotecaDaoLocal.getDirector()).thenReturn(new ArrayList<>());
        when(videotecaDaoLocal.getGenre()).thenReturn(new ArrayList<>());
        when(videotecaDaoLocal.getAllDVDs()).thenReturn(new ArrayList<>());
        when(videotecaDaoLocal.getAvailableDVDs()).thenReturn(new ArrayList<>());

        when(request.getRequestDispatcher("/dashboardUser.jsp")).thenReturn(requestDispatcher);

        // Esegui il metodo processRequest
        loginServlet.processRequest(request, response);

        // Verifiche
        verify(session).setAttribute("user", mockUser);
        verify(session).setAttribute("role", "USER");
        verify(request).setAttribute("directors", new ArrayList<>());
        verify(request).setAttribute("genres", new ArrayList<>());
        verify(request).setAttribute("dvds", new ArrayList<>());
        verify(request).setAttribute("availableDVDs", new ArrayList<>());
        verify(requestDispatcher).forward(request, response);
    }


    @Test // Autenticazione non valida
    void testProcessInvalidUser() throws ServletException, IOException {
        when(request.getParameter("username")).thenReturn("user");
        when(request.getParameter("password")).thenReturn("wrongPassword");
        when(videotecaDaoLocal.checkUser("user", "wrongPassword")).thenReturn(null);
        when(request.getRequestDispatcher("/login.jsp")).thenReturn(requestDispatcher);

        when(request.getSession()).thenReturn(session);
        
        loginServlet.processRequest(request, response);

        // Verifica l'errore
        verify(request).setAttribute("error", "CREDENZIALI ERRATE");
        verify(requestDispatcher).forward(request, response);
    }
}

