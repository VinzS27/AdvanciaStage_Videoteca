package com.advancia.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.advancia.DAO.VideotecaDAOLocal;
import com.advancia.model.DVD;
import com.advancia.model.Director;
import com.advancia.model.Genre;
import com.advancia.model.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public LoginServlet() {
		super();
	}

	@EJB
	private VideotecaDAOLocal videotecaDaoLocal;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();

		List<Director> directors = new ArrayList<Director>();
		List<Genre> genres = new ArrayList<Genre>();
		List<DVD> dvds = new ArrayList<DVD>();
		List<DVD> availableDVDs = new ArrayList<DVD>();
		User user = videotecaDaoLocal.checkUser(username, password);
		
		if (user != null) {
			session.setAttribute("user", user);
			session.setAttribute("role", user.getRole());
			directors = videotecaDaoLocal.getDirector();
			genres = videotecaDaoLocal.getGenre();
			dvds = videotecaDaoLocal.getAllDVDs();
			availableDVDs = videotecaDaoLocal.getAvailableDVDs();
			
			request.setAttribute("directors", directors);
			request.setAttribute("genres", genres);
			request.setAttribute("dvds", dvds);
			request.setAttribute("availableDVDs", availableDVDs);
			
			if ("ADMIN".equals(user.getRole())) {
				request.getRequestDispatcher("/dashboardAdmin.jsp").forward(request, response);
			} else if ("USER".equals(user.getRole())) {
				request.getRequestDispatcher("/dashboardUser.jsp").forward(request, response);
			}
		} else {
			request.setAttribute("error", "CREDENZIALI ERRATE");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
}
