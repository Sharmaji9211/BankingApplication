package com.shiva.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shiva.models.Account;
import com.shiva.models.TransactionInfo;
import com.shiva.repositories.AccountRepository;
import com.shiva.repositories.TransactionRepository;
import com.shiva.services.AccountService;
import com.shiva.utility.DateTimeUtility;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired private TransactionRepository transactionRepository;

	@Autowired private DateTimeUtility dateTimeUtility;
	private AccountRepository accountRepository;
	public AccountServiceImpl(AccountRepository accountRepository) {
		this.accountRepository=accountRepository;
	}
	@Override
	public int getAmount(int accountno) {
		
		return accountRepository.getAmount(accountno);
	}
	@Override
	public TransactionInfo deposit(int amount, int accountno) {
		Account account=accountRepository.findById(accountno).orElse(null);
		account.setAmount(account.getAmount()+amount);
		accountRepository.save(account);
		TransactionInfo transactionInfo=saveTransaction(amount, accountno, "Credit");
		
		return transactionInfo;
	}
	@Override
	public TransactionInfo withdraw(int amount, int accountno) {
		Account account=accountRepository.findById(accountno).orElse(null);
		account.setAmount(account.getAmount()-amount);
		accountRepository.save(account);
		TransactionInfo transactionInfo=saveTransaction(amount, accountno, "Debite");
		return transactionInfo;
	}
	private TransactionInfo saveTransaction(int amount, int accountno, String type) {
	    TransactionInfo transactionInfo=new TransactionInfo();
	    transactionInfo.setFromaccount(accountno);
	    transactionInfo.setToaccount(accountno);
	    transactionInfo.setType(type);
	    transactionInfo.setAmount(amount);
	    transactionInfo.setDate(dateTimeUtility.getCurrentDate());
	    transactionInfo.setTime(dateTimeUtility.getCurrentTime());
	    transactionRepository.save(transactionInfo);
		return transactionInfo;
	}
	@Override
	public boolean existAccountno(int accountno) {
		
		return accountRepository.existsById(accountno);
	}
	
	
	
}
