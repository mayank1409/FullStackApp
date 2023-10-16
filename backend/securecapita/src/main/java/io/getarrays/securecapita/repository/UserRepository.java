package io.getarrays.securecapita.repository;

import java.util.Collection;

import io.getarrays.securecapita.domain.User;

public interface UserRepository<T extends User> {
	/* Basic CRUD OPERATIONS */
	T create(T data);

	Collection<T> list(int page, int size);

	T get(Long id);

	T update(Long id);

	Boolean delete(Long id);
}
