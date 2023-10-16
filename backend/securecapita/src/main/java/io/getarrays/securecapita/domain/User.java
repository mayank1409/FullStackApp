package io.getarrays.securecapita.domain;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_DEFAULT)
public class User {

	private long id;

	@NotEmpty(message = "First Name can not be empty")
	private String firstName;

	@NotEmpty(message = "Last Name can not be empty")
	private String lastName;

	@NotEmpty(message = "Email can not be empty")
	@Email(message = "Invalid Email")
	private String email;

	private String password;

	private String address;

	private String phone;

	private String title;

	private String bio;

	private String imageUrl;

	private boolean enabled;

	private boolean isNotLocked;

	private boolean isUsingMfa;

	private LocalDateTime createdDate;

}
