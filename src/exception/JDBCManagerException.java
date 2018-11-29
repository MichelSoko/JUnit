package exception;

public class JDBCManagerException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private Exception cause;

	public JDBCManagerException(Exception cause) {
		this.cause = cause;
	}

	@Override
	public String toString() {
		return new String("JDBCManager : " + cause.getMessage());
	}

}
