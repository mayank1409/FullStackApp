package io.getarrays.securecapita.query;

public class UserQuery {
	
	public static final String EMAIL_COUNT_QUERY = "SELECT COUNT(*) FROM users WHERE email = :email";
	
	public static final String INSERT_USER_QUERY = "INSERT INTO users(first_name, last_name, email, password) VALUES(:firstName, :lastName, :email, :password)";
	
	public static final String INSERT_ACCOUNT_VERIFICATION_URL = "INSERT INTO account_verification(user_id, url) VALUES(:userId, :url)";



}
