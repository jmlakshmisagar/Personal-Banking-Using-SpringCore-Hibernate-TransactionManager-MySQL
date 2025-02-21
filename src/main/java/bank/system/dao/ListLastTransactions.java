package bank.system.dao;

import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import bank.system.pojo.Account;
import bank.system.pojo.Customer;
import bank.system.pojo.Transactions;

public class ListLastTransactions {
	private SessionFactory sessionFactory;

	public ListLastTransactions(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public String listTransactions() {
		Scanner scanner = new Scanner(System.in);
		Session session = sessionFactory.openSession();
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

			System.out.print("Enter number of transactions to show: ");
			int numTransactions = scanner.nextInt();

			Query<Transactions> query = session
					.createQuery("FROM Transactions WHERE account_id = :accountId ORDER BY transaction_date DESC",
							Transactions.class)
					.setParameter("accountId", account.getId()).setMaxResults(numTransactions);

			List<Transactions> transactions = query.getResultList();

			System.out.println("Last " + numTransactions + " transactions:");
			for (Transactions trans : transactions) {
				System.out.println(trans.toString());
			}

			result = "Transactions listed successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "Failed to list transactions: " + e.getMessage();
		} finally {
			session.close();
			scanner.close();
		}

		return result;
	}

}
