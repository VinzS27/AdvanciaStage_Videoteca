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

@WebServlet("/DashboardServletAdmin")
public class DashboardServletAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public DashboardServletAdmin() {
        super(); 
        }

    @EJB
	private VideotecaDAOLocal videotecaDaoLocal;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String action = request.getParameter("actions");

		if (action != null) {
			switch (action) {
				case "addDVD":
					addNewDVD(request, response);
					break;
				case "rentDVD":
					rentDVD(request, response);
					break;
				case "deleteDVD":
					deleteDVD(request, response);
				default:
					break;
			}
			request.getRequestDispatcher("/dashboardAdmin.jsp").forward(request, response);
		}
	}

	private void addNewDVD(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String dvdName = request.getParameter("DVDName");
		long genreId = Long.parseLong(request.getParameter("id_genre"));
		long directorId = Long.parseLong(request.getParameter("id_director"));

		videotecaDaoLocal.addDVD(dvdName, directorId, genreId);

		request.setAttribute("dvds", videotecaDaoLocal.getAllDVDs());
		request.setAttribute("directors", videotecaDaoLocal.getDirector());
		request.setAttribute("genres", videotecaDaoLocal.getGenre());
	}

	private void deleteDVD(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User user = (User) request.getSession().getAttribute("user");
		long id = Long.parseLong(request.getParameter("id_dvd"));

		videotecaDaoLocal.deleteDVD(id);
		user.setListDVD(videotecaDaoLocal.getDVDByUser(user.getId()));

		request.setAttribute("dvds", videotecaDaoLocal.getAllDVDs());
		request.setAttribute("directors", videotecaDaoLocal.getDirector());
		request.setAttribute("genres", videotecaDaoLocal.getGenre());
	}

	public void rentDVD(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User user = (User) request.getSession().getAttribute("user");
		long id_dvd = Long.parseLong(request.getParameter("id_dvd"));
		
		videotecaDaoLocal.rentDVD(user.getId(),id_dvd);

		request.setAttribute("dvds", videotecaDaoLocal.getAllDVDs());
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
