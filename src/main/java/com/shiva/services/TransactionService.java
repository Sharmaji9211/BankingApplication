package com.shiva.services;

import java.util.List;

import com.shiva.models.TransactionInfo;

public interface TransactionService {

	List<TransactionInfo> getStatements(int accountno);

	TransactionInfo tranfer(int amount, int raccountno, int accountno);

}
