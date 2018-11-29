package util;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exception.JDBCManagerException;

public class JDBCManager {

	// attributs
    private static JDBCManager INSTANCE = new JDBCManager();
	private static Connection connexion;

	// constructeur privé (design pattern Singleton)
	private JDBCManager() {
	}
	
	// point d'accès pour l'instance unique du JDBCManager (design pattern Singleton)
    public static JDBCManager getInstance() {   
    	return INSTANCE;
    }
    
    public static Connection getConnexion() throws JDBCManagerException {
    	
    	try {
    		// chargement du driver
    		try {
				Class.forName("org.postgresql.Driver").getConstructor().newInstance();
			} catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException
					| SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
			// ouverture de la connexion
			connexion = DriverManager.getConnection("jdbc:postgresql://localhost/annu", "user_lambda", "lambda_password");
			
		} catch (SQLException e) {
			// handle any errors
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
			
		} catch (InstantiationException e) {
			throw new JDBCManagerException(e);
		} catch (IllegalAccessException e) {
			throw new JDBCManagerException(e);
		} catch (ClassNotFoundException e) {
			throw new JDBCManagerException(e);
		}
    	return connexion;
    }

}