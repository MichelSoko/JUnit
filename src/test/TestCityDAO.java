package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bo.CityBO;
import dao.CityDAO;
import exception.CityDAOException;
import exception.JDBCManagerException;

class TestCityDAO {

	private CityDAO cityDAO = new CityDAO();

	@BeforeEach
	public void setUp() throws Exception {
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	// Test : Obtenir une ville par son nom
	@Test
	public void testGetByName() {
		try {
			CityBO cityRecovered = cityDAO.getByName("Paris");
			assertEquals(1, cityRecovered.getId_city());
			assertEquals("Paris", cityRecovered.getName());
			assertEquals("Anne Hidalgo", cityRecovered.getMayor());
			assertEquals(2206488, cityRecovered.getInhabitants());
			assertEquals("75000", cityRecovered.getPostalcode());
		} catch (CityDAOException | JDBCManagerException e) {
			e.printStackTrace();
			fail("Echec du test : obtenir une ville par son nom !");
		}
	}

	// Test : Obtenir une ville par son identifiant
	@Test
	public void testGetById() {
		try {
			CityBO cityRecovered = cityDAO.getById(1);
			assertEquals(1, cityRecovered.getId_city());
			assertEquals("Paris", cityRecovered.getName());
			assertEquals("Anne Hidalgo", cityRecovered.getMayor());
			assertEquals(2206488, cityRecovered.getInhabitants());
			assertEquals("75000", cityRecovered.getPostalcode());
		} catch (CityDAOException | JDBCManagerException e) {
			e.printStackTrace();
			fail("Echec du test : obtenir une ville par son id !");
		}
	}
	
	// Test : Vérifier l'existence d'une ville dans la BDD
	@Test 
	public void testExist() {
		try {
			// cas où la ville existe
			CityBO cityExpected1 = new CityBO("Nice", "Christian Estrosi", 342295, "06000");
			cityExpected1.setId_city(5);
			assertEquals(true, cityDAO.exist(cityExpected1));
			
			// cas où la ville n'existe pas
			CityBO cityExpected2 = new CityBO("fake-city", "fake-mayor", 50, "fake-postalcode");
			cityExpected2.setId_city(9999);
			assertEquals(false, cityDAO.exist(cityExpected2));
			
		} catch (CityDAOException | JDBCManagerException e) {
			e.printStackTrace();
			fail("Echec du test : vérifier l'existence d'une ville !");
		}
	}
	
	// Test : Insérer une ville dans la BDD
	@Test
	public void testAdd() {
		try {
			CityBO cityBO = new CityBO("test-name", "test-mayor", 50, "test-postalcode");
			cityDAO.add(cityBO);	
			CityBO cityRecovered = cityDAO.getByName("test-name");
			assertEquals(true, cityDAO.exist(cityRecovered));
		} catch (CityDAOException | JDBCManagerException e) {
			e.printStackTrace();
			fail("Echec du test : insérer une ville dans la BDD !");
		}
	}
	
	// Test : Supprimer une ville dans la BDD
	@Test
	public void testDelete() {
		try {
			CityBO cityRecovered = cityDAO.getByName("test-name");
			cityDAO.delete(cityRecovered);
			assertEquals(false, cityDAO.exist(cityRecovered));
		} catch (CityDAOException | JDBCManagerException e) {
			e.printStackTrace();
			fail("Echec du test : supprimer une ville dans la BDD !");
		}
	}
	
	// Test : Mettre à jour une ville dans la BDD
	@Test
	public void testUpdate() {
		try {
			// insertion d'une ville
			CityBO cityBO = new CityBO("test-name-update", "test-mayor-update", 50, "01234");
			cityDAO.add(cityBO);
			// modifier cette ville
			CityBO cityToUpdate = cityDAO.getByName("test-name-update");
			cityToUpdate.setMayor("test-mayor-updated");
			cityToUpdate.setInhabitants(100);
			cityToUpdate.setPostalcode("56789");
			// modifier en BDD
			cityDAO.update(cityToUpdate);		
			CityBO cityUpdated = cityDAO.getByName("test-name-update");
			assertEquals("test-mayor-updated", cityUpdated.getMayor());
			assertEquals(100, cityUpdated.getInhabitants());
			assertEquals("56789", cityUpdated.getPostalcode());
			// supprimer la villetest de la BDD
			cityDAO.delete(cityUpdated);
			
		} catch (CityDAOException | JDBCManagerException e) {
			e.printStackTrace();
			fail("Echec du test : mettre à jour une ville dans la BDD !");
		}
	}

}
