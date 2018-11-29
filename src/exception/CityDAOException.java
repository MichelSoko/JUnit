package exception;

public class CityDAOException extends Exception {

	private static final long serialVersionUID = 1L;
	private Exception cause;
	
	public CityDAOException(Exception cause) {
		this.cause = cause;
	}

	@Override
	public String toString() {
		return new String("CityDAO : " + cause.getMessage());
	}
	
}