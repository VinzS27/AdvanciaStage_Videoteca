package com.advancia.controller;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.advancia.DAO.VideotecaDAOLocal;
import com.advancia.model.User;

@WebServlet("/DashboardServletUser")
public class DashboardServletUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public DashboardServletUser() {
        super(); 
        }

    @EJB
	private VideotecaDAOLocal videotecaDaoLocal;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String action = request.getParameter("actions");

		if (action != null && action.equals("rentDVD")) {
			rentDVD(request, response);
			request.getRequestDispatcher("/dashboardUser.jsp").forward(request, response);
		}
	}

	public void rentDVD(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User user = (User) request.getSession().getAttribute("user");
		long id_dvd = Long.parseLong(request.getParameter("id_dvd"));
		
		videotecaDaoLocal.rentDVD(user.getId(),id_dvd);

		request.setAttribute("availableDVDs", videotecaDaoLocal.getAvailableDVDs());
		request.setAttribute("directors", videotecaDaoLocal.getDirector());
		request.setAttribute("genres", videotecaDaoLocal.getGenre());
		
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
