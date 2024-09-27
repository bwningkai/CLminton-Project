package Model;

public class UserCredential {
	private static String loggedUserEmail;

	public static String getLoggedUserEmail() {
		return loggedUserEmail;
	}

	public static void setLoggedUserEmail(String loggedUserEmail) {
		UserCredential.loggedUserEmail = loggedUserEmail;
	}

}
