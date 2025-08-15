package com.shiva.services.impl;

import org.springframework.stereotype.Service;

import com.shiva.models.Account;
import com.shiva.models.User;
import com.shiva.repositories.AccountRepository;
import com.shiva.repositories.UserRepository;
import com.shiva.services.UserService;

@Service
public class UserServiceImpl implements UserService {


	private UserRepository userRepository;
	private AccountRepository accountRepository;
	public UserServiceImpl (UserRepository userRepository, AccountRepository accountRepository) {
		this.userRepository=userRepository;
		this.accountRepository=accountRepository;
	}
	public int createUser(User user) {
		userRepository.save(user);
		Account account=new Account();
		account.setUserId(user.getUserid());
		accountRepository.save(account);
		return account.getAccountno();
	}
	public boolean userExist(String userid) {
		User n=userRepository.findById(userid).orElse(null);
		if(n==null) {
			return false;
		}
		return true;
	}
	public User getUser(String userid) {
		
		return userRepository.findById(userid).orElse(null);
	}
	public int getAccountNo(String userid) {
		
		return accountRepository.getAccountNo(userid);
	}
	@Override
	public User getUser(int raccountno) {
		Account ac=accountRepository.findById(raccountno).orElse(null);
		String userid=ac.getUserId();
		User user=userRepository.findById(userid).orElse(null);
		return user;
	}}
