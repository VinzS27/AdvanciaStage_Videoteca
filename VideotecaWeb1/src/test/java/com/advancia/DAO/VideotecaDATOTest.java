package com.advancia.DAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.advancia.model.DVD;
import com.advancia.model.Director;
import com.advancia.model.Genre;
import com.advancia.model.User;

//permette di utilizzare le annotazioni @InjectMocks e @Mock senza bisogno di inizializzarle con setUp()
@ExtendWith(MockitoExtension.class) 
class VideotecaDATOTest {

		//Crea un'istanza di VideotecaDAO e inserisce al suo interno i mock (finti oggetti creati con Mockito). 
	 	@InjectMocks
	    private VideotecaDAO videotecaDAO;

	 	//oggetto simulato che può essere configurato per restituire valori specifici
	 	//e testare i metodi di VideotecaDAO senza dover accedere a un database reale.
	    @Mock
	    private EntityManager em;

	    @Mock
	    private TypedQuery<DVD> queryDVD;

	    @Mock
	    private TypedQuery<User> queryUser;

	    @Mock
	    private TypedQuery<Director> queryDirector;

	    @Mock
	    private TypedQuery<Genre> queryGenre;

	    //Inizializza le annotazioni di Mockito (@Mock e @InjectMocks) per la classe di test.
	    //Con @ExtendWith(MockitoExtension.class), questo metodo setUp è opzionale.
	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    public void testGetAllDVDs() {
	        DVD dvd1 = new DVD("Inception", null, null, null);
	        DVD dvd2 = new DVD("Titanic", null, null, null);
	        List<DVD> dvds = Arrays.asList(dvd1, dvd2);

	        when(em.createQuery("SELECT d from DVD d", DVD.class)).thenReturn(queryDVD);
	        when(queryDVD.getResultList()).thenReturn(dvds);

	        List<DVD> result = videotecaDAO.getAllDVDs();
	        assertNotNull(result);
	        assertEquals(2, result.size());
	        assertEquals("Inception", result.get(0).getName());
	    }

	    @Test
	    public void testGetDVDById() {
	        DVD dvd = new DVD("Inception", null, null, null);
	        long dvdId = 1L;

	        when(em.find(DVD.class, dvdId)).thenReturn(dvd);

	        DVD result = videotecaDAO.getDVDById(dvdId);
	        assertNotNull(result);
	        assertEquals("Inception", result.getName());
	    }

	    @Test
	    public void testGetDVDByUser() {
	        User user = new User();
	        user.setId(1L);
	        DVD dvd1 = new DVD("Inception", null, null, null);
	        DVD dvd2 = new DVD("Titanic", null, null, null);
	        List<DVD> dvds = new ArrayList<>(Arrays.asList(dvd1, dvd2));
	        user.setListDVD(dvds);

	        when(em.find(User.class, user.getId())).thenReturn(user);

	        List<DVD> result = videotecaDAO.getDVDByUser(user.getId());
	        assertNotNull(result);
	        assertEquals(2, result.size());
	    }

	    @Test
	    public void testGetAvailableDVDs() {
	        DVD dvd1 = new DVD("Inception", null, null, null);
	        DVD dvd2 = new DVD("Titanic", null, null, null);
	        List<DVD> dvds = Arrays.asList(dvd1, dvd2);

	        when(em.createQuery("SELECT d FROM DVD d WHERE d.rentalDate IS NULL", DVD.class)).thenReturn(queryDVD);
	        when(queryDVD.getResultList()).thenReturn(dvds);

	        List<DVD> result = videotecaDAO.getAvailableDVDs();
	        assertNotNull(result);
	        assertEquals(2, result.size());
	    }

	    @Test
	    public void testAddDVD() {
	        Long directorId = 1L;
	        Long genreId = 1L;
	        String dvdName = "Inception";

	        when(em.createQuery("select d from DVD d where d.name = :name ", DVD.class)).thenReturn(queryDVD);
	        when(queryDVD.getResultList()).thenReturn(new ArrayList<>());

	        videotecaDAO.addDVD(dvdName, directorId, genreId);

	        verify(em).persist(any(DVD.class));
	    }

	    @Test
	    public void testDeleteDVD() {
	        long dvdId = 1L;
	        DVD dvd = new DVD("Inception", null, null, null);

	        when(em.find(DVD.class, dvdId)).thenReturn(dvd);

	        videotecaDAO.deleteDVD(dvdId);

	        verify(em).remove(dvd);
	    }

	    @Test
	    public void testRentDVD() {
	        DVD dvd = new DVD("Inception", null, null, null);
	        dvd.setRentalDate(null);
	        User user = new User();
	        user.setId(1L);
	        user.setListDVD(new ArrayList<>());

	        long userId = 1L;
	        long dvdId = 1L;

	        when(em.find(DVD.class, dvdId)).thenReturn(dvd);
	        when(em.find(User.class, userId)).thenReturn(user);

	        videotecaDAO.rentDVD(userId, dvdId);

	        assertNotNull(dvd.getRentalDate());
	        assertTrue(user.getListDVD().contains(dvd));
	        verify(em).merge(dvd);
	        verify(em).merge(user);
	    }

	    @Test
	    public void testGetDirector() {
	        Director director1 = new Director();
	        Director director2 = new Director();
	        List<Director> directors = Arrays.asList(director1, director2);

	        when(em.createQuery("SELECT d from Director d", Director.class)).thenReturn(queryDirector);
	        when(queryDirector.getResultList()).thenReturn(directors);

	        List<Director> result = videotecaDAO.getDirector();
	        assertNotNull(result);
	        assertEquals(2, result.size());
	    }

	    @Test
	    public void testGetDirectorById() {
	        long directorId = 1L;
	        Director director = new Director();

	        when(em.find(Director.class, directorId)).thenReturn(director);

	        Director result = videotecaDAO.getDirectorById(directorId);
	        assertNotNull(result);
	    }

	    @Test
	    public void testGetGenre() {
	        Genre genre1 = new Genre();
	        Genre genre2 = new Genre();
	        List<Genre> genres = Arrays.asList(genre1, genre2);

	        when(em.createQuery("SELECT g from Genre g", Genre.class)).thenReturn(queryGenre);
	        when(queryGenre.getResultList()).thenReturn(genres);

	        List<Genre> result = videotecaDAO.getGenre();
	        assertNotNull(result);
	        assertEquals(2, result.size());
	    }

	    @Test
	    public void testGetGenreById() {
	        long genreId = 1L;
	        Genre genre = new Genre();

	        when(em.find(Genre.class, genreId)).thenReturn(genre);

	        Genre result = videotecaDAO.getGenreById(genreId);
	        assertNotNull(result);
	    }
}
