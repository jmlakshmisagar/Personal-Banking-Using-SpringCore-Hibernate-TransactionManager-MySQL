package bank.system.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import bank.system.pojo.Customer;

public class CustomerCreation {
	@SuppressWarnings("unused")
	private SessionFactory sessionFactory;

	public CustomerCreation(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public String createCustomer(Customer customer, SessionFactory sessionFactory, SessionFactory sessionFactory2) {
		String result;
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();
			try {
				session.persist(customer);
				transaction.commit();
				result = "Success: Customer created successfully.";
			} catch (Exception e) {
				if (transaction != null) {
					transaction.rollback();
				}
				result = "Failed to create customer. Reason: " + e.getMessage();
			}
		} catch (Exception e) {
			result = "Failed to open session. Reason: " + e.getMessage();
		}
		return result;
	}
}
