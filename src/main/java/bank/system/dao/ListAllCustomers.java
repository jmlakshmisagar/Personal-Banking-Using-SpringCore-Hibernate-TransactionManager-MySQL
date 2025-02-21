package bank.system.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class ListAllCustomers {
	private SessionFactory sessionFactory;

	public ListAllCustomers(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public String getAllCustomersWithAccountBalance() {
		Session session = sessionFactory.openSession();
		List<Object[]> customersWithBalance = null;
		String result;
		try {
			String hql = "SELECT c.id, c.name, c.mobileNumber, a.balance "
					+ "FROM Customer c INNER JOIN Account a ON c.id = a.customerId";
			Query<Object[]> query = session.createQuery(hql, Object[].class);
			customersWithBalance = query.getResultList();
			result = "Success: Retrieved all customers with account balances.";

			for (Object[] customer : customersWithBalance) {
				System.out.println("Customer ID: " + customer[0] + ", Name: " + customer[1] + ", Mobile Number: "
						+ customer[2] + ", Balance: " + customer[3]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "Failed to retrieve customers with account balances. Reason: " + e.getMessage();
		} finally {
			session.close();
		}
		return result;
	}
}
