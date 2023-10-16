package io.getarrays.securecapita.enums;

public enum VerificationType {
	
	ACCOUNT("ACCOUNT"),
	PASSWORD("PASSWORD");
	
	private final String type;
	
	private VerificationType(String type) {
		this.type = type;
	}

	public String getType() {
		return type.toLowerCase();
	}
	

}
