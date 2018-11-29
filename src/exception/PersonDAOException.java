package exception;

public class PersonDAOException extends Exception {

	private static final long serialVersionUID = 1L;
	private Exception cause;

	public PersonDAOException(Exception cause) {
		this.cause = cause;
	}

	@Override
	public String toString() {
		return new String("PersonDAO : " + cause.getMessage());
	}

}