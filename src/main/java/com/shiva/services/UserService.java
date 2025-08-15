package com.shiva.services;

import com.shiva.models.User;

public interface UserService {

	int createUser(User user);

	boolean userExist(String userid);

	User getUser(String userid);

	int getAccountNo(String userid);

	User getUser(int raccountno);

}
