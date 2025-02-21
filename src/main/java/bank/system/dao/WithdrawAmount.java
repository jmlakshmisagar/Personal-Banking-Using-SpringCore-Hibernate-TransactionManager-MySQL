package bank.system.dao;

import java.util.Scanner;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import bank.system.pojo.Account;
import bank.system.pojo.Customer;
import bank.system.pojo.Transactions; // To avoid naming conflict with Hibernate Transaction class

public class WithdrawAmount {
	private SessionFactory sessionFactory;

	public WithdrawAmount(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public String withdrawFromAccount() {
		Scanner scanner = new Scanner(System.in);
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
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

			System.out.print("Enter amount to withdraw: ");
			double amount = scanner.nextDouble();

			if (amount > account.getBalance()) {
				result = "Insufficient balance!";
				return result;
			}

			// Begin transaction
			transaction = session.beginTransaction();

			// Update account balance
			account.setBalance(account.getBalance() - amount);
			session.update(account);

			// Create a new Transaction entry for this withdrawal
			Transactions Transactions = new Transactions();
			Transactions.setAccountId(account.getId());
			Transactions.setTransactionType("Debit");
			Transactions.setAmount(amount);
			Transactions.setTransactionDate(new java.sql.Timestamp(new Date().getTime()));

			// Save the transaction
			session.save(Transactions);

			// Commit the transaction
			transaction.commit();

			result = "Withdrawal successful! New Balance: " + account.getBalance();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			result = "Withdrawal failed: " + e.getMessage();
		} finally {
			session.close();
			scanner.close();
		}

		return result;
	}
}
