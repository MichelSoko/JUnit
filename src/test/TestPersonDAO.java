package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bo.CityBO;
import bo.PersonBO;
import dao.PersonDAO;
import exception.JDBCManagerException;
import exception.PersonDAOException;

class TestPersonDAO {

	private PersonDAO personDAO = new PersonDAO();
	
	@BeforeEach
	public void setUp() throws Exception {
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	// Test : Obtenir une personne par son email
	@Test
	public void testGetByEmails() {
		try {
			PersonBO personRecovered = personDAO.getByEmails("jean.legrand@gmail.com");
			// vérification de la personne
			assertEquals(1, personRecovered.getId_person());
			assertEquals("Jean", personRecovered.getFirstname());
			assertEquals("Legrand", personRecovered.getLastname());
			assertEquals("jean.legrand@gmail.com", personRecovered.getEmails());
			assertEquals("0619285544", personRecovered.getPhone());
			// vérification de la ville de la personne
			assertEquals(1, personRecovered.getCityBO().getId_city());
			assertEquals("Paris", personRecovered.getCityBO().getName());
			assertEquals("Anne Hidalgo", personRecovered.getCityBO().getMayor());
			assertEquals(2206488, personRecovered.getCityBO().getInhabitants());
			assertEquals("75000", personRecovered.getCityBO().getPostalcode());
		} catch (PersonDAOException | JDBCManagerException e) {
			e.printStackTrace();
			fail("Echec du test : obtenir une ville par son email !");
		}
	}
	
	// Test : Obtenir une personne par son identifiant
	@Test
	public void testGetById() {
		try {
			CityBO cityExpected = new CityBO("Toulouse", "Jean-Luc Moudenc", 471941, "31000");
			cityExpected.setId_city(4);
			PersonBO personExpected = new PersonBO(cityExpected, "Marie", "Masson", "marie.masson@bbox.fr", "0769451821");
			PersonBO personRecovered = personDAO.getById(5);
			assertEquals(personExpected.toString(), personRecovered.toString());
		} catch (PersonDAOException | JDBCManagerException e) {
			e.printStackTrace();
			fail("Echec du test : obtenir une ville par son id !");
		}
	}
	
	// Test : Vérifier l'existence d'une personne dans la BDD
	@Test 
	public void testExist() {
		try {
			// cas où la personne existe
			CityBO cityExpected1 = new CityBO("Montpellier", "Phillipe Saurel", 282243, "34000");
			cityExpected1.setId_city(7);
			PersonBO personExpected1 = new PersonBO(cityExpected1, "Louis", "Fournier", "louis.fournier@laposte.net", "0724259182");
			personExpected1.setId_person(5);
			assertEquals(true, personDAO.exist(personExpected1));
			
			// cas où la personne n'existe pas
			CityBO cityExpected2 = new CityBO("fake-city", "fake-mayor", 50, "fake-postalcode");
			cityExpected2.setId_city(9999);
			PersonBO personExpected2 = new PersonBO(cityExpected2, "fake-firstname", "fake-lastname", "fake@email.com", "0635656565");
			personExpected2.setId_person(9999);
			assertEquals(false, personDAO.exist(personExpected2));
			
		} catch (PersonDAOException | JDBCManagerException e) {
			e.printStackTrace();
			fail("Echec du test : vérifier l'existence d'une personne !");
		}
	}
		
	// Test : Insérer une personne dans la BDD
	@Test
	public void testAdd() {
		try {
			CityBO cityBO = new CityBO("Nantes", "Johanna Rolland", 303382, "44000");
			cityBO.setId_city(6);
			PersonBO personBO = new PersonBO(cityBO, "test-firstname", "test-lastname", "test@email.com", "test-phone");
			personDAO.add(personBO);
			PersonBO personRecovered = personDAO.getByEmails("test@email.com");
			assertEquals(true, personDAO.exist(personRecovered));
		} catch (PersonDAOException | JDBCManagerException e) {
			e.printStackTrace();
			fail("Echec du test : insérer une ville dans la BDD !");
		}
	}
	
	// Test : Supprimer une personne dans la BDD
	@Test
	public void testDelete() {
		try {
			PersonBO personRecovered = personDAO.getByEmails("test@email.com");
			personDAO.delete(personRecovered);
			assertEquals(false, personDAO.exist(personRecovered));
		} catch (PersonDAOException | JDBCManagerException e) {
			e.printStackTrace();
			fail("Echec du test : supprimer une ville dans la BDD !");
		}
	}
	
	// Test : Mettre à jour une personne dans la BDD
	@Test
	public void testUpdate() {
		try {
			// insertion d'une personne
			CityBO cityBO = new CityBO("Nantes", "Johanna Rolland", 303382, "44000");
			cityBO.setId_city(6);
			PersonBO personBO = new PersonBO(cityBO, "firstnameToUpdate", "lastnameToUpdate", "testUpdate@email.com", "0612345678");
			personDAO.add(personBO);
			// modifier cette personne
			PersonBO personToUpdate = personDAO.getByEmails("testUpdate@email.com");
			personToUpdate.setFirstname("firstnameUpdated");
			personToUpdate.setLastname("lastnameUpdated");
			personToUpdate.setPhone("0787654321");
			CityBO otherCityBO = new CityBO("Lyon", "Gérard Collomb", 500715, "69000");
			otherCityBO.setId_city(3);
			personToUpdate.setCityBO(otherCityBO);
			// modifier en BDD
			personDAO.update(personToUpdate);
			PersonBO personUpdated = personDAO.getByEmails("testUpdate@email.com");
			assertEquals("firstnameUpdated", personUpdated.getFirstname());
			assertEquals("lastnameUpdated", personUpdated.getLastname());
			assertEquals("0787654321", personUpdated.getPhone());
			assertEquals(3, personUpdated.getCityBO().getId_city());
			// supprimer la personnetest
			personDAO.delete(personUpdated);
			
		} catch (PersonDAOException | JDBCManagerException e) {
			e.printStackTrace();
			fail("Echec du test : mettre à jour une ville dans la BDD !");
		}
	}

}
