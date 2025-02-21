package bank.system.util;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import bank.system.pojo.Account;
import bank.system.pojo.Customer;
import bank.system.pojo.Transactions;

public class HibernateUtil {

	public static SessionFactory sessionFactory;

	public static void initializeSessionFactory() {
		Configuration configuration = new Configuration();
		Properties settings = new Properties();
		settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
		settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/BankingSystem?useSSL=false");
		settings.put("hibernate.connection.username", "root");
		settings.put("hibernate.connection.password", "root@39");
		settings.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		settings.put("hibernate.show_sql", "false");
		settings.put("hibernate.format_sql", "false");

		configuration.setProperties(settings);
		configuration.addAnnotatedClass(Customer.class);
		configuration.addAnnotatedClass(Account.class);
		configuration.addAnnotatedClass(Transactions.class);

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}
}
