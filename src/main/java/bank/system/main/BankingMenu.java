package bank.system.main;

import java.util.Scanner;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import bank.system.service.CustomerService;
import bank.system.service.TransactionService;
import bank.system.util.HibernateUtil;
import java.util.NoSuchElementException;

public class BankingMenu {

	public static void main(String[] args) throws SecurityException, RollbackException, HeuristicMixedException,
			HeuristicRollbackException, SystemException {
		HibernateUtil.initializeSessionFactory();
		runBankingMenu();
	}

	private static void runBankingMenu() throws SecurityException, RollbackException, HeuristicMixedException,
			HeuristicRollbackException, SystemException {
		Scanner scanner = new Scanner(System.in);
		CustomerService customerService = new CustomerService();
		TransactionService transactionService = new TransactionService();
		boolean exit = false;

		while (!exit) {
			DisplayMenu.displayMenu();
			System.out.print("Enter your choice: ");

			try {
				if (scanner.hasNextLine()) {
					String input = scanner.nextLine();
					int choice = Integer.parseInt(input.trim());
					switch (choice) {
					case 1:
						customerService.createCustomer(scanner);
						break;
					case 2:
						transactionService.addAmount(scanner);
						break;
					case 3:
						transactionService.withdrawAmount(scanner);
						break;
					case 4:
						transactionService.checkBalance(scanner);
						break;
					case 5:
						customerService.listAllCustomers();
						break;
					case 6:
						transactionService.listLastTransactions(scanner);
						break;
					case 7:
						transactionService.transactAmount(scanner);
						break;
					case 8:
						exit = true;
						break;
					default:
						System.out.println("Invalid choice! Please try again.");
					}
				} else {
					System.out.println("No more input available. Exiting.");
					exit = true;
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input! Please enter a valid number.");
			} catch (NoSuchElementException e) {
				System.out.println("No more input available. Exiting.");
				exit = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		scanner.close();
	}
}
