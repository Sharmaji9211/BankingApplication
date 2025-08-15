package com.shiva.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shiva.models.Account;
import com.shiva.models.TransactionInfo;
import com.shiva.repositories.AccountRepository;
import com.shiva.repositories.TransactionRepository;
import com.shiva.services.TransactionService;
import com.shiva.utility.DateTimeUtility;

@Service
public class TransactionServiceImpl implements TransactionService{

	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired private DateTimeUtility dateTimeUtility;
	@Autowired private AccountRepository accountRepository;

	@Override
	public List<TransactionInfo> getStatements(int accountno) {
		
		return transactionRepository.getTransactionList(accountno);
	}
	private TransactionInfo transferTransaction(int amount, int accountno,int raccountno, String type) {
	    TransactionInfo transactionInfo=new TransactionInfo();
	    transactionInfo.setFromaccount(accountno);
	    transactionInfo.setToaccount(raccountno);
	    transactionInfo.setType(type);
	    transactionInfo.setAmount(amount);
	    transactionInfo.setDate(dateTimeUtility.getCurrentDate());
	    transactionInfo.setTime(dateTimeUtility.getCurrentTime());
	    transactionRepository.save(transactionInfo);
		return transactionInfo;
	}
	@Override
	public TransactionInfo tranfer(int amount, int raccountno, int accountno) {
		Account senderAccount=accountRepository.findById(accountno).orElse(null);
		Account recieverAccount=accountRepository.findById(raccountno).orElse(null);
		
		senderAccount.setAmount(senderAccount.getAmount()-amount);
		recieverAccount.setAmount(recieverAccount.getAmount()+amount);
		accountRepository.save(senderAccount);
		accountRepository.save(recieverAccount);
		TransactionInfo transactionInfo= transferTransaction(amount, accountno,raccountno, "Transfer");
		transferTransaction(amount,  raccountno,accountno,"Credit");
		return transactionInfo;
	}
	
	
}
