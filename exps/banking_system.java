import java.util.Scanner;

public class banking_system {

	static class Account {
		private final String name;
		private final String accountNumber;
		private double balance;

		Account(String name, String accountNumber, double initialBalance) {
			this.name = name;
			this.accountNumber = accountNumber;
			this.balance = initialBalance;
		}

		void deposit(double amount) {
			if (amount <= 0) {
				System.out.println("Deposit amount must be positive.");
				return;
			}
			balance += amount;
			System.out.println("Deposit successful. New balance: " + balance);
		}

		void withdraw(double amount) {
			if (amount <= 0) {
				System.out.println("Withdrawal amount must be positive.");
				return;
			}
			if (amount > balance) {
				System.out.println("Insufficient balance. Withdrawal denied.");
				return;
			}
			balance -= amount;
			System.out.println("Withdrawal successful. New balance: " + balance);
		}

		void displayDetails() {
			System.out.println("\nAccount Details");
			System.out.println("Name: " + name);
			System.out.println("Account Number: " + accountNumber);
			System.out.println("Balance: " + balance);
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter account holder name: ");
		String name = scanner.nextLine();

		System.out.print("Enter account number: ");
		String accountNumber = scanner.nextLine();

		System.out.print("Enter initial balance: ");
		double initialBalance = scanner.nextDouble();

		Account account = new Account(name, accountNumber, initialBalance);

		while (true) {
			System.out.println("\n1. Deposit");
			System.out.println("2. Withdraw");
			System.out.println("3. Display Account Details");
			System.out.println("4. Exit");
			System.out.print("Choose an option: ");

			int choice = scanner.nextInt();

			switch (choice) {
				case 1:
					System.out.print("Enter deposit amount: ");
					account.deposit(scanner.nextDouble());
					break;
				case 2:
					System.out.print("Enter withdrawal amount: ");
					account.withdraw(scanner.nextDouble());
					break;
				case 3:
					account.displayDetails();
					break;
				case 4:
					System.out.println("Exiting system.");
					scanner.close();
					return;
				default:
					System.out.println("Invalid option.");
			}
		}
	}
}
