package bank.system.dao;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import bank.system.pojo.Account;
import bank.system.pojo.Customer;

public class TransactAmount {
	private SessionFactory sessionFactory;

	public TransactAmount(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void debitAndCreditAmount() {
		Scanner scanner = new Scanner(System.in);
		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {
			System.out.print("Enter debiting customer ID: ");
			int debitingCustomerId = scanner.nextInt();

			Customer debitingCustomer = session.get(Customer.class, debitingCustomerId);
			if (debitingCustomer == null) {
				System.out.println("Customer not found!");
				return;
			}
			Account debitingAccount = session.createQuery("FROM Account WHERE customer_id = :customerId", Account.class)
					.setParameter("customerId", debitingCustomerId).uniqueResult();

			if (debitingAccount == null) {
				System.out.println("Account not found for customer!");
				return;
			}

			System.out.println("Customer Id: " + debitingCustomer.getId());
			System.out.println("Customer Name: " + debitingCustomer.getName());
			System.out.println("Current Balance: " + debitingAccount.getBalance());

			System.out.print("Enter amount to debit: ");
			double amount = scanner.nextDouble();

			if (amount > debitingAccount.getBalance()) {
				System.out.println("Insufficient balance!");
				return;
			}

			System.out.print("Enter crediting customer ID: ");
			int creditingCustomerId = scanner.nextInt();

			Customer creditingCustomer = session.get(Customer.class, creditingCustomerId);
			if (creditingCustomer == null) {
				System.out.println("Customer not found!");
				return;
			}
			Account creditingAccount = session
					.createQuery("FROM Account WHERE customer_id = :customerId", Account.class)
					.setParameter("customerId", creditingCustomerId).uniqueResult();

			if (creditingAccount == null) {
				System.out.println("Account not found for customer!");
				return;
			}

			System.out.println("Crediting Customer Name: " + creditingCustomer.getName());

			transaction = session.beginTransaction();
			debitingAccount.setBalance(debitingAccount.getBalance() - amount);
			creditingAccount.setBalance(creditingAccount.getBalance() + amount);
			session.update(debitingAccount);
			session.update(creditingAccount);
			transaction.commit();

			System.out.println("Transactions successful!");

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			System.out.println("Transactions failed: " + e.getMessage());
		} finally {
			session.close();
			scanner.close();
		}
	}
}
