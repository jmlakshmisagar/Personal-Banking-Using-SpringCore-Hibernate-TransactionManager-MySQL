package bank.system.service;

import java.util.Scanner;
import bank.system.dao.AddAmount;
import bank.system.dao.BalanceCheck;
import bank.system.dao.ListLastTransactions;
import bank.system.dao.TransactAmount;
import bank.system.dao.WithdrawAmount;
import bank.system.util.HibernateUtil;

public class TransactionService {

	public void addAmount(Scanner scanner) {
		AddAmount addAmount = new AddAmount(HibernateUtil.sessionFactory);
		String result = addAmount.addToAccount();
		System.out.println(result);
	}

	public void withdrawAmount(Scanner scanner) {
		WithdrawAmount withdrawAmount = new WithdrawAmount(HibernateUtil.sessionFactory);
		String result = withdrawAmount.withdrawFromAccount();
		System.out.println(result);
	}

	public void checkBalance(Scanner scanner) {
		System.out.print("Enter customer ID: ");
		int customerId = scanner.nextInt();
		BalanceCheck balanceCheck = new BalanceCheck(HibernateUtil.sessionFactory);
		String result = balanceCheck.checkCustomerBalance(customerId);
		System.out.println(result);
	}

	public void listLastTransactions(Scanner scanner) {
		ListLastTransactions listLastTransactions = new ListLastTransactions(HibernateUtil.sessionFactory);
		String result = listLastTransactions.listTransactions();
		System.out.println(result);
	}

	public void transactAmount(Scanner scanner) {
		TransactAmount transactAmount = new TransactAmount(HibernateUtil.sessionFactory);
		transactAmount.debitAndCreditAmount();
	}
}
