package bank.system.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import bank.system.pojo.Account;
import bank.system.pojo.Customer;

public class BalanceCheck {
	private SessionFactory sessionFactory;

	public BalanceCheck(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public String checkCustomerBalance(int customerId) {
		Session session = sessionFactory.openSession();
		String result;
		try {
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

			result = "Customer Name: " + customer.getName() + ", Balance: " + account.getBalance();
		} catch (Exception e) {
			e.printStackTrace();
			result = "Failed to retrieve customer balance. Reason: " + e.getMessage();
		} finally {
			session.close();
		}
		return result;
	}
}
