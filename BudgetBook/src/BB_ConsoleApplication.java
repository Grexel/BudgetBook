import java.util.Scanner;
import java.util.*;

public class BB_ConsoleApplication {

	private Scanner sc;
	private BB_Account account;
	public static void main(String[] args)
	{
		BB_ConsoleApplication mainProgram = new BB_ConsoleApplication();
	}
	
	public BB_ConsoleApplication()
	{
		boolean quit = false;
		sc = new Scanner(System.in);
		account = new BB_Account();

		insertTestData();
		
		while(!quit)
		{
			showMenu();
			int choice = getInput(1,10,sc);
			switch(choice)
			{
				case 1:
					viewBook();
					break;
				case 2:
					//viewTransactions();
					break;
				case 3:
					//addTransaction(sc);
					break;
				case 4:
					//addCategory(sc);
					break;
				case 5:
					//addSubCategory(sc);
					break;
				case 6:
					//addItemToCategory(sc);
					break;
				case 7:
					//loadLedger(sc);
					break;
				case 8:
					//saveLedger();
					break;
				case 9:
					//newLedger(sc);
					break;
				case 10:
					quit = true;
					break;
				default:
					break;
			}
			System.out.println("\nPress Enter to continue");
			sc.nextLine();
		}

	}
	public void showMenu()
	{
		System.out.println("====================");
		System.out.println("1: View Book");
		System.out.println("2: View Transactions");
		System.out.println("3: Add Transaction");
		System.out.println("4: Add Category");
		System.out.println("5: Add SubCategory");
		System.out.println("6: Add Item to Category");
		System.out.println("7: Load Ledger");
		System.out.println("8: Save Ledger");
		System.out.println("9: New Ledger");
		System.out.println("10: Quit");
		System.out.println("====================");
	}
	public void viewBook()
	{
		double monthlyPayments = 0;
		double monthlyEarnings = 0;
		
		System.out.println("================");
		System.out.println("Viewing Account.");
		System.out.println("================\n");
		System.out.println(new Date());
		for(BB_Debt debt : account.debts())
		{
			if(debt.isCurrent())
			{
				System.out.println("$ " + debt.name() + " " + debt.payment());
				monthlyPayments += debt.payment();
			}
			else
				System.out.println("  " + debt.name() + " " + debt.payment());
		}
		for(BB_Utility utility : account.utilities())
		{
			if(utility.isCurrent())
			{
				System.out.println("$ " + utility.name() + " " + 
					utility.getPayment(new Date()).costPerEach());
				monthlyPayments += utility.getPayment(new Date()).costPerEach();
			}
			else
				System.out.println("  " + utility.name() + " " + utility.averagePayment());
		}
		for(BB_Disposable dispose : account.spendingTabs())
		{
			System.out.println(dispose.name() + ":");
			for(BB_Receipt receipt : dispose.getReceipts(new Date()))
			{
				System.out.println("  " + receipt.name() + " " + receipt.total());
				monthlyPayments += receipt.total();
			}
		}
		System.out.println("Monthly Payments = " + monthlyPayments);
	}

	public int getInput(int min, int max, Scanner sc)
	{
		boolean inputCorrect = false;
		int inputInt = 0;
		while(!inputCorrect)
		{
			inputInt = sc.nextInt();
			sc.nextLine();
			if(inputInt <= max && inputInt >= min)
				inputCorrect = true;
			else
				System.out.println("Please enter an integer between " + min + " and " + max + ".");
		}
		return inputInt;
	}
	public void insertTestData()
	{
		BB_Debt dbt = new BB_Debt();
		dbt.name("Test Debt");
		dbt.initialBalance(16000);
		dbt.currentBalance(16000);
		dbt.payment(500);
		dbt.principalPayment(350);
		dbt.interestPayment(150);
		account.addDebt(dbt);
		account.addDebtPayment("Test Debt");
	}
	/*	
	public void viewTransactions()
	{
		System.out.println("$$$$$$$$$$$$$");
		System.out.println("Transactions.");
		System.out.println("$$$$$$$$$$$$$\n");
		for(BB_Item transact : ledger.transactions())
		{
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			System.out.println(transact.itemNumber() + " " + df.format(transact.itemDate())
				+ " " + transact.itemName() + " " + transact.itemCost());
		}
	}
	public void addTransaction(Scanner sc)
	{
		String name = "";
		double cost = 0;

		System.out.print("Enter Name of Item: ");
		name = sc.nextLine();
		System.out.print("Enter the cost: ");
		cost = Double.parseDouble(sc.nextLine());
		int itemNum = ledger.transactions().size();
		ledger.addTransaction(itemNum, name, cost, new Date());

	}
	public void addCategory(Scanner sc)
	{
		String name = "";
		System.out.print("Enter Name of Category: ");
		name = sc.nextLine();
		ledger.addCategory(name);
	}
	public void addSubCategory(Scanner sc)
	{
		String ParentName = "";
		String SubName = "";
		System.out.print("Enter Name of Parent Category: ");
		ParentName = sc.nextLine();
		System.out.print("Enter Name of Sub Category: ");
		SubName = sc.nextLine();
		ledger.addCategoryToCategory(ParentName, SubName);
	}
	public void addItemToCategory(Scanner sc)
	{
		String catName = "";
		String itemName = "";
		System.out.print("Enter Name of Item: ");
		itemName = sc.nextLine();
		System.out.print("Enter Name of Category: ");
		catName = sc.nextLine();
		ledger.addItemToCategory(itemName, catName);
	}
	public void loadLedger(Scanner sc)
	{
		System.out.println("Please enter the file name you want to open.");
		ledger = BB_FileStorage.loadLedger(sc.nextLine());
	}
	public void saveLedger()
	{
		System.out.println("Saving ledger...");
		BB_FileStorage.saveLedger(ledger.ledgerName() + ".txt", ledger);
	}
	public void newLedger(Scanner sc)
	{
		System.out.println("Creating new ledger...");
		System.out.println("Please enter the Name of the ledger: ");
		ledger = new BB_Ledger(sc.nextLine());
	}
	*/
}
