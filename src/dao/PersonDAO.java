package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bo.CityBO;
import bo.PersonBO;
import exception.JDBCManagerException;
import exception.PersonDAOException;
import util.JDBCManager;

public class PersonDAO implements InterfaceDAO<PersonBO> {

	private Connection jdbcConnection;

	public PersonDAO() {
		JDBCManager.getInstance();
	}

	// Connexion à la BDD
	protected void connect() throws JDBCManagerException {
		try {
			if (jdbcConnection == null || jdbcConnection.isClosed()) {
				// récupération de la connexion
				jdbcConnection = JDBCManager.getConnexion();
			}
		} catch (SQLException e) {
			throw new JDBCManagerException(e);
		}
	}

	// Déconnexion de la BDD
	protected void disconnect() throws JDBCManagerException {
		try {
			if (jdbcConnection != null && !jdbcConnection.isClosed()) {
				try {
					// fermeture de la connexion
					jdbcConnection.close();
				} catch (SQLException ex) {
					ex.getMessage();
				}
			}
		} catch (Exception e) {
			throw new JDBCManagerException(e);
		}
	}
	
	// Insérer une personne dans la BDD
	@Override
	public boolean add(PersonBO personBO) throws PersonDAOException, JDBCManagerException {
		try {
			String sql = "INSERT INTO person (id_city, firstname, lastname, emails, phone) "
					+ "VALUES (?, ?, ?, ?, ?)";
			connect();
			
			PreparedStatement statement = jdbcConnection.prepareStatement(sql);

			int id_city = personBO.getCityBO().getId_city();
			statement.setInt(1, id_city);
			statement.setString(2, personBO.getFirstname());
			statement.setString(3, personBO.getLastname());
			statement.setString(4, personBO.getEmails());
			statement.setString(5, personBO.getPhone());

			boolean rowInserted = statement.executeUpdate() > 0;
			statement.close();
			disconnect();
			return rowInserted;
		} catch (SQLException e) {
			throw new PersonDAOException(e);
		}
	}

	// Mettre à jour une personne dans la BDD
	@Override
	public boolean update(PersonBO personBO) throws PersonDAOException, JDBCManagerException {
		try {
			String sql = "UPDATE person "
					+ "SET id_city = ?, firstname = ?, lastname = ?, emails = ?, phone = ? "
					+ "WHERE id_person = ?";
			connect();

			PreparedStatement statement = jdbcConnection.prepareStatement(sql);

			int id_city = personBO.getCityBO().getId_city();
			statement.setInt(1, id_city);
			statement.setString(2, personBO.getFirstname());
			statement.setString(3, personBO.getLastname());
			statement.setString(4, personBO.getEmails());
			statement.setString(5, personBO.getPhone());
			statement.setInt(6, personBO.getId_person());

			boolean rowUpdated = statement.executeUpdate() > 0;
			statement.close();
			disconnect();
			return rowUpdated;
		} catch (SQLException e) {
			throw new PersonDAOException(e);
		}
	}

	// Supprimer une ville dans la BDD
	@Override
	public boolean delete(PersonBO personBO) throws PersonDAOException, JDBCManagerException {
		try {
			String sql = "DELETE FROM person "
					+ "WHERE id_person = ?";
			connect();

			PreparedStatement statement = jdbcConnection.prepareStatement(sql);
			statement.setInt(1, personBO.getId_person());

			boolean rowDeleted = statement.executeUpdate() > 0;
			statement.close();
			disconnect();
			return rowDeleted;
		} catch (SQLException e) {
			throw new PersonDAOException(e);
		}
	}

	// Obtenir toutes les personnes
	@Override
	public List<PersonBO> getAll() throws PersonDAOException, JDBCManagerException {
		try {
			List<PersonBO> listPersonBO = new ArrayList<PersonBO>();
			String sql = "SELECT c.id_city, c.name, c.mayor, c.inhabitants, c.postalcode, "
					+ "p.id_person, p.firstname, p.lastname, p.emails, p.phone "
					+ "FROM person p "
					+ "INNER JOIN city c ON p.id_city = c.id_city";
			connect();

			Statement statement = jdbcConnection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {		
				// Construction d'une ville
				int id_city = resultSet.getInt("id_city");
				String name = resultSet.getString("name");
				String mayor = resultSet.getString("mayor");
				int inhabitants = resultSet.getInt("inhabitants");
				String postalcode = resultSet.getString("postalcode");			
				CityBO cityBO = new CityBO(name, mayor, inhabitants, postalcode);
				cityBO.setId_city(id_city);
				
				// Construction d'une personne
				int id_person = resultSet.getInt("id_person");
				String firstname = resultSet.getString("firstname");
				String lastname = resultSet.getString("lastname");
				String emails = resultSet.getString("emails");
				String phone = resultSet.getString("phone");
				PersonBO personBO = new PersonBO(cityBO, firstname, lastname, emails, phone);
				personBO.setId_person(id_person);
				
				// Ajout d'une personne à la liste
				listPersonBO.add(personBO);
			}

			resultSet.close();
			statement.close();
			disconnect();
			return listPersonBO;
		} catch (SQLException e) {
			throw new PersonDAOException(e);
		}
	}
	
	// Obtenir une personne par son ID
	@Override
	public PersonBO getById(int id_person) throws PersonDAOException, JDBCManagerException {
		try {
			PersonBO personBO = null;
			String sql = "SELECT c.id_city, c.name, c.mayor, c.inhabitants, c.postalcode, "
					+ "p.firstname, p.lastname, p.emails, p.phone "
					+ "FROM person p "
					+ "INNER JOIN city c "
					+ "ON p.id_city = c.id_city "
					+ "WHERE id_person = ?";
			connect();

			PreparedStatement statement = jdbcConnection.prepareStatement(sql);
			statement.setInt(1, id_person);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {	
				// Construction d'une ville
				int id_city = resultSet.getInt("id_city");
				String name = resultSet.getString("name");
				String mayor = resultSet.getString("mayor");
				int inhabitants = resultSet.getInt("inhabitants");
				String postalcode = resultSet.getString("postalcode");	
				CityBO cityBO = new CityBO(name, mayor, inhabitants, postalcode);
				cityBO.setId_city(id_city);
				
				// Construction d'une personne
				String firstname = resultSet.getString("firstname");
				String lastname = resultSet.getString("lastname");
				String emails = resultSet.getString("emails");
				String phone = resultSet.getString("phone");
				personBO = new PersonBO(cityBO, firstname, lastname, emails, phone);
				personBO.setId_person(id_person);		
			}

			resultSet.close();
			statement.close();
			return personBO;
		} catch (SQLException e) {
			throw new PersonDAOException(e);
		}
	}

	// Obtenir une personne par son emails
	public PersonBO getByEmails(String emails) throws PersonDAOException, JDBCManagerException {
		try {
			PersonBO personBO = null;
			String sql = "SELECT c.id_city, c.name, c.mayor, c.inhabitants, c.postalcode, "
					+ "p.id_person, p.firstname, p.lastname, p.phone "
					+ "FROM person p "
					+ "INNER JOIN city c "
					+ "ON p.id_city = c.id_city "
					+ "WHERE emails = ?";
			connect();

			PreparedStatement statement = jdbcConnection.prepareStatement(sql);
			statement.setString(1, emails);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {	
				// Construction d'une ville
				int id_city = resultSet.getInt("id_city");
				String name = resultSet.getString("name");
				String mayor = resultSet.getString("mayor");
				int inhabitants = resultSet.getInt("inhabitants");
				String postalcode = resultSet.getString("postalcode");	
				CityBO cityBO = new CityBO(name, mayor, inhabitants, postalcode);
				cityBO.setId_city(id_city);
				
				// Construction d'une personne
				int id_person = resultSet.getInt("id_person");
				String firstname = resultSet.getString("firstname");
				String lastname = resultSet.getString("lastname");
				String phone = resultSet.getString("phone");
				personBO = new PersonBO(cityBO, firstname, lastname, emails, phone);
				personBO.setId_person(id_person);		
			}

			resultSet.close();
			statement.close();
			return personBO;
		} catch (SQLException e) {
			throw new PersonDAOException(e);
		}
	}

	// Obtenir une liste de personne pour une ville
	public List<PersonBO> getByCity(CityBO cityBO) throws PersonDAOException, JDBCManagerException {
		try {
			List<PersonBO> listPersonBO = new ArrayList<PersonBO>();
			String sql = "SELECT p.id_person, p.firstname, p.lastname, p.emails, p.phone "
					+ "FROM person p "
					+ "INNER JOIN city c ON p.id_city = c.id_city "
					+ "WHERE c.name = ?";
			connect();

			PreparedStatement statement = jdbcConnection.prepareStatement(sql);
			statement.setString(1, cityBO.getName());
			ResultSet resultSet = statement.executeQuery();	

			while (resultSet.next()) {			
				// Construction d'une personne
				int id_person = resultSet.getInt("id_person");
				String firstname = resultSet.getString("firstname");
				String lastname = resultSet.getString("lastname");
				String emails = resultSet.getString("emails");
				String phone = resultSet.getString("phone");
				PersonBO personBO = new PersonBO(cityBO, firstname, lastname, emails, phone);
				personBO.setId_person(id_person);
				
				// Ajout d'une personne à la liste
				listPersonBO.add(personBO);
			}

			resultSet.close();
			statement.close();
			disconnect();
			return listPersonBO;
		} catch (SQLException e) {
			throw new PersonDAOException(e);		
		}
	}
	
	// Obtenir une liste de personne par le nom de famille
	public List<PersonBO> getByLastname(String lastname) throws PersonDAOException, JDBCManagerException {
		try {
			List<PersonBO> listPersonBO = new ArrayList<PersonBO>();
			String sql = "SELECT c.id_city, c.name, c.mayor, c.inhabitants, c.postalcode, "
					+ "p.id_person, p.firstname, p.emails, p.phone "
					+ "FROM person p "
					+ "INNER JOIN city c "
					+ "ON p.id_city = c.id_city "
					+ "WHERE lastname = ?";
			connect();

			PreparedStatement statement = jdbcConnection.prepareStatement(sql);
			statement.setString(1, lastname);	
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				// Construction d'une ville
				int id_city = resultSet.getInt("id_city");
				String name = resultSet.getString("name");
				String mayor = resultSet.getString("mayor");
				int inhabitants = resultSet.getInt("inhabitants");
				String postalcode = resultSet.getString("postalcode");		
				CityBO cityBO = new CityBO(name, mayor, inhabitants, postalcode);
				cityBO.setId_city(id_city);
				
				// Construction d'une personne
				int id_person = resultSet.getInt("id_person");
				String firstname = resultSet.getString("firstname");
				String emails = resultSet.getString("emails");
				String phone = resultSet.getString("phone");
				PersonBO personBO = new PersonBO(cityBO, firstname, lastname, emails, phone);
				personBO.setId_person(id_person);
				
				// Ajout d'une personne à la liste
				listPersonBO.add(personBO);
			}

			resultSet.close();
			statement.close();
			disconnect();
			return listPersonBO;
		} catch (SQLException e) {
			throw new PersonDAOException(e);
		}
	}

	// Vérifier l'existence d'une personne dans la BDD
	@Override
	public boolean exist(PersonBO personBO) throws PersonDAOException, JDBCManagerException {
		boolean result = false;
		if (this.getById(personBO.getId_person()) != null)
			result = true;
		return result;
	}	

}
