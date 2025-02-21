package bank.system.service;

import java.util.Scanner;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import bank.system.dao.CustomerCreation;
import bank.system.dao.ListAllCustomers;
import bank.system.pojo.Account;
import bank.system.pojo.Customer;
import bank.system.util.HibernateUtil;

public class CustomerService {

	public void createCustomer(Scanner scanner) throws SecurityException, RollbackException, HeuristicMixedException,
			HeuristicRollbackException, SystemException {
		System.out.print("Enter customer name: ");
		String name = scanner.next();
		System.out.print("Enter mobile number: ");
		String mobileNumber = scanner.next();
		Customer customer = new Customer();
		customer.setName(name);
		customer.setMobileNumber(mobileNumber);

		CustomerCreation customerCreation = new CustomerCreation(HibernateUtil.sessionFactory);
		String result = customerCreation.createCustomer(customer, HibernateUtil.sessionFactory,
				HibernateUtil.sessionFactory);

		if (result.startsWith("Success")) {
			System.out.print("Enter initial balance: ");
			double balance = scanner.nextDouble();
			Account account = new Account();
			account.setCustomerId(customer.getId());
			account.setBalance(balance);

			try (Session session = HibernateUtil.sessionFactory.openSession()) {
				Transaction transaction = session.beginTransaction();
				session.persist(account);
				transaction.commit();
				System.out.println("Account created successfully!");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Failed to create account: " + e.getMessage());
			}
		} else {
			System.out.println(result);
		}
	}

	public void listAllCustomers() {
		ListAllCustomers listAllCustomers = new ListAllCustomers(HibernateUtil.sessionFactory);
		String result = listAllCustomers.getAllCustomersWithAccountBalance();
		System.out.println(result);
	}
}
