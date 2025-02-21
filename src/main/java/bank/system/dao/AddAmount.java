package bank.system.dao;

import java.util.Scanner;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import bank.system.pojo.Account;
import bank.system.pojo.Customer;
import bank.system.pojo.Transactions; // Correctly using Transactions class

public class AddAmount {
	private SessionFactory sessionFactory;

	public AddAmount(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public String addToAccount() {
		Scanner scanner = new Scanner(System.in);
		Session session = sessionFactory.openSession();
		org.hibernate.Transaction transaction = null; // Hibernate Transaction object
		String result;

		try {
			System.out.print("Enter customer ID: ");
			int customerId = scanner.nextInt();

			Customer customer = session.get(Customer.class, customerId);
			if (customer == null) {
				result = "Customer not found!";
				return result;
			}

			Account account = session.createQuery("FROM Account WHERE customer_id = :customerId", Account.class)
					.setParameter("customerId", customerId).uniqueResult();

			if (account == null) {
				result = "Account not found for customer!";
				return result;
			}

			System.out.println("Customer Name: " + customer.getName());
			System.out.println("Current Balance: " + account.getBalance());

			System.out.print("Enter amount to add: ");
			double amount = scanner.nextDouble();

			// Begin transaction
			transaction = session.beginTransaction();

			// Update account balance
			account.setBalance(account.getBalance() + amount);
			session.update(account);

			// Create a new Transactions entry for this deposit
			Transactions transactions = new Transactions();
			transactions.setAccountId(account.getId());
			transactions.setTransactionType("Credit");
			transactions.setAmount(amount);
			transactions.setTransactionDate(new java.sql.Timestamp(new Date().getTime()));

			// Save the transaction to the Transactions table
			session.save(transactions);

			// Commit the transaction
			transaction.commit();

			result = "Amount added successfully! New Balance: " + account.getBalance();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			result = "Failed to add amount: " + e.getMessage();
		} finally {
			session.close();
			scanner.close();
		}

		return result;
	}
}
