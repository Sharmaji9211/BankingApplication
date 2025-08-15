package com.shiva.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shiva.models.User;

public interface UserRepository extends JpaRepository<User, String> {
	

}
