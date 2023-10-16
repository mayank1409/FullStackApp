package io.getarrays.securecapita.exception;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = -3593092943254973138L;
	
	private String message;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMessage() {
		return message;
	}

	public ApiException(String message) {
		super(message);
		this.message = message;
	}

	
}
