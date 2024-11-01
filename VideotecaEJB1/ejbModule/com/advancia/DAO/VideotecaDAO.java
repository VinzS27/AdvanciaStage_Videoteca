package com.advancia.DAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.advancia.model.DVD;
import com.advancia.model.Director;
import com.advancia.model.Genre;
import com.advancia.model.User;

@Stateless
@LocalBean
public class VideotecaDAO implements VideotecaDAOLocal {

	public VideotecaDAO() {
	}

	@PersistenceContext
	private EntityManager em;

	// --------USER----------
	@Override
	public User checkUser(String username, String password) {
		List<User> listResult = new ArrayList<User>();

		TypedQuery<User> query = em
				.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password=:password", User.class);
		query.setParameter("username", username);
		query.setParameter("password", password);
		listResult = query.getResultList();

		return listResult.isEmpty() ? null : listResult.get(0);
	}

	// ----------DVD----------

	@Override
	public List<DVD> getAllDVDs() {
		List<DVD> listResult = new ArrayList<DVD>();
		em.flush();

		TypedQuery<DVD> query = em.createQuery("SELECT d from DVD d", DVD.class);
		listResult = query.getResultList();

		return listResult.isEmpty() ? null : listResult;
	}

	@Override
	public DVD getDVDById(long id) {
		em.flush();
		DVD dvd = em.find(DVD.class, id);

		return (dvd != null) ? dvd : null;
	}

	@Override
	public List<DVD> getDVDByUser(Long id) {
		em.flush();
		User user = em.find(User.class, id);
		
		List<DVD> listResult = user.getListDVD();

		return listResult.isEmpty() ? null : listResult;
	}

	@Override
	public List<DVD> getAvailableDVDs() {
		em.flush();
		return em.createQuery("SELECT d FROM DVD d WHERE d.rentalDate IS NULL", DVD.class).getResultList();
	}

	@Override
	public void addDVD(String name, Long directorId , Long genreId) {
		em.flush();
		TypedQuery<DVD> query = em.createQuery("select d from DVD d where d.name = :name ", DVD.class);
		query.setParameter("name", name);
		List<DVD> listResult = query.getResultList();

		if (listResult == null || listResult.isEmpty()) {
			Director director = getDirectorById(directorId);
			Genre genre = getGenreById(genreId);
			DVD dvd = new DVD(name, director, genre, null);
			em.persist(dvd);
			em.flush();
		}
	}

	@Override
	public void deleteDVD(Long id) {
		em.flush();
		DVD dvd = em.find(DVD.class, id);
		if (dvd != null) {
			em.remove(dvd);
			em.flush();
		}
	}

	@Override
	public void rentDVD(Long userId, Long dvdId) {
		em.flush();
		DVD dvd = em.find(DVD.class, dvdId);
		User user = em.find(User.class, userId);

		if (dvd != null && user != null && dvd.getRentalDate() == null) {
			dvd.setRentalDate(new Date());
			dvd.getUsers().add(user);
			user.getListDVD().add(dvd);
			em.merge(dvd);
			em.merge(user);
			em.flush();
		}
	}

	// ----------DIRECTOR----------
	@Override
	public List<Director> getDirector() {
		List<Director> listResult = new ArrayList<Director>();
		em.flush();

		TypedQuery<Director> query = em.createQuery("SELECT d from Director d", Director.class);
		listResult = query.getResultList();

		return listResult.isEmpty() ? null : listResult;
	}

	@Override
	public Director getDirectorById(long idDirector) {
		em.flush();
		Director director = em.find(Director.class, idDirector);

		return (director != null) ? director : null;
	}

	@Override
	public Director getDirectorByNameAndLastname(String name, String lastname) {
		List<Director> listResult = new ArrayList<Director>();
		em.flush();

		TypedQuery<Director> query = em.createQuery(
				"SELECT d from Director d where d.name = :name and" + "d.lastname = :lastname", Director.class);
		query.setParameter("name", name);
		query.setParameter("lastname", lastname);

		listResult = query.getResultList();

		return listResult.isEmpty() ? null : listResult.get(0);
	}
	// ----------GENRE----------

	@Override
	public List<Genre> getGenre() {
		List<Genre> listResult = new ArrayList<Genre>();
		em.flush();

		TypedQuery<Genre> query = em.createQuery("SELECT g from Genre g", Genre.class);
		listResult = query.getResultList();

		return listResult.isEmpty() ? null : listResult;
	}

	@Override
	public Genre getGenreById(long idGenre) {
		em.flush();
		Genre genre = em.find(Genre.class, idGenre);
		return (genre != null) ? genre : null;
	}

}
