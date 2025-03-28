package com.advancia.DAO;

import java.util.List;

import javax.ejb.Local;

import com.advancia.model.DVD;
import com.advancia.model.Director;
import com.advancia.model.Genre;
import com.advancia.model.User;

@Local
public interface VideotecaDAOLocal {
	
	//USER
	User checkUser(String username, String password);
	
	//DVD
	List<DVD> getAllDVDs();
	DVD getDVDById(long id) ;
	List<DVD> getDVDByUser(Long id);
	List<DVD> getAvailableDVDs();
	void addDVD(String name, Long directorId, Long genreId);
    void deleteDVD(Long id);
    void rentDVD(Long userId,Long dvdId);
     
    //DIRECTOR
	List<Director> getDirector();
	Director getDirectorById(long idDirector);
	Director getDirectorByNameAndLastname(String name, String lastname);

	//GENRE
	List<Genre> getGenre();
	Genre getGenreById(long idGenre);
	
}