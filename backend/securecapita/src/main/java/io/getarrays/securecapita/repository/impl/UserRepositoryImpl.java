package io.getarrays.securecapita.repository.impl;

import static io.getarrays.securecapita.enums.RoleType.ROLE_USER;
import static io.getarrays.securecapita.enums.VerificationType.ACCOUNT;
import static io.getarrays.securecapita.query.UserQuery.*;
import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.getarrays.securecapita.domain.Role;
import io.getarrays.securecapita.domain.User;
import io.getarrays.securecapita.exception.ApiException;
import io.getarrays.securecapita.repository.RoleRepository;
import io.getarrays.securecapita.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User> {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final RoleRepository<Role> roleRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Transactional
	@Override
	public User create(User user) {
		if (getEmailCount(user.getEmail().trim().toLowerCase()) > 0) {
			throw new ApiException("Email Already in Use. Please use different email address");
		}
		try {
			KeyHolder holder = new GeneratedKeyHolder();
			SqlParameterSource source = getSqlParameterSource(user);
			jdbcTemplate.update(INSERT_USER_QUERY, source, holder);
			user.setId(requireNonNull(holder.getKey().longValue()));
			roleRepository.addRoleToUser(user.getId(), ROLE_USER.name());
			String url = getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());
			jdbcTemplate.update(INSERT_ACCOUNT_VERIFICATION_URL, Map.of("userId", user.getId(), "url", url));

			user.setEnabled(false);
			user.setNotLocked(false);
			return user;
		} catch (Exception exception) {
			log.error(exception.getMessage());
			throw new ApiException("An Error occurred. Please try again later");
		}
	}

	@Override
	public Collection<User> list(int page, int size) {
		return null;
	}

	@Override
	public User get(Long id) {
		return null;
	}

	@Override
	public User update(Long id) {
		return null;
	}

	@Override
	public Boolean delete(Long id) {
		return null;
	}

	private int getEmailCount(String email) {
		return jdbcTemplate.queryForObject(EMAIL_COUNT_QUERY, Map.of("email", email), Integer.class);
	}

	private SqlParameterSource getSqlParameterSource(User user) {
		return new MapSqlParameterSource().addValue("firstName", user.getFirstName())
				.addValue("lastName", user.getLastName()).addValue("email", user.getEmail())
				.addValue("password", bCryptPasswordEncoder.encode(user.getPassword()));
	}

	private String getVerificationUrl(String key, String type) {
		return ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/verify/" + type + "/" + key)
				.toUriString();
	}
}
