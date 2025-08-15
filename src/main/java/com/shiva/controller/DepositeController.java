package com.shiva.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.shiva.models.TransactionInfo;
import com.shiva.services.AccountService;

@Controller
@RequestMapping("services/deposite")
public class DepositeController {
	@Autowired private AccountService accountService;
	
	@GetMapping("/deposite-form")
	public String getDepositeForm() {
		return "service/deposite/deposite-money-form";
	}
	@GetMapping("/deposit.do")
	public ModelAndView depositeMoney(int amount, @SessionAttribute int accountno) {
		
		if(amount <0) {
			ModelAndView modelAndView=new ModelAndView("service/deposite/deposite-money-form");
			modelAndView.addObject("amount", amount);
			modelAndView.addObject("msg", "Amount should be more than 0 ");
			return modelAndView;
			
		}
		
		TransactionInfo transaction= accountService.deposit(amount, accountno);
		ModelAndView modelAndView=new ModelAndView("service/deposite/deposite-money-success");
		modelAndView.addObject("transaction", transaction);
		return modelAndView;
		
	}
	
	

}
