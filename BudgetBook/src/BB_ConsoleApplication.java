import java.util.*;

public class BB_ConsoleApplication {

	private Scanner sc;
	private BB_Account account;
	public static void main(String[] args)
	{
		BB_ConsoleApplication mainProgram = new BB_ConsoleApplication();
		mainProgram.run();
	}
	
	public BB_ConsoleApplication()
	{
		sc = new Scanner(System.in);
		account = new BB_Account();
		insertTestData();
	}
	public void run()
	{
		boolean quit = false;
		while(!quit)
		{
			showMainMenu();
			int choice = getInt(1,12,sc);
			switch(choice)
			{
				case 1:
					viewMonthlyAccount();
					break;
				case 2:
					showAddSectionMenu();
					addSection();
					break;
				case 3:
					showAddPayMenu();
					addPay();
					break;
				case 4:
					showEditSectionMenu();
					break;
				case 5:
					showEditPayMenu();
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
					quit = true;
					break;
				case 10:
					//
					break;
				case 11:
					//
					break;
				default:
					break;
			}
			System.out.println("\nPress Enter to continue");
			sc.nextLine();
		}
		
	}
	
	public void showMainMenu()
	{
		System.out.println("====================");
		System.out.println("1: View Monthly Log        10: View Timespan");
		System.out.println("2: Add D/U/I/S/P/C         11: Estimate Future Expenses");
		System.out.println("3: Add Payment/Earning     ");
		System.out.println("4: Edit D/U/I/S/P/C        ");
		System.out.println("5: Edit Payment/Earning    ");
		System.out.println("6: Save Account            ");
		System.out.println("7: Load Account            ");
		System.out.println("8: New Account             ");
		System.out.println("9: Quit");
		System.out.println("====================");
		/*
		 view month/timespan, whole or d/u/i/s, based on profile/category/name
		 
		 */
	}
	public void showAddSectionMenu()
	{
		System.out.println("====================");
		System.out.println("Add Section");
		System.out.println("====================");
		System.out.println("1: Add Debt");
		System.out.println("2: Add Utility");
		System.out.println("3: Add Income");
		System.out.println("4: Add Disposable Tab");
		System.out.println("5: Add Profile");
		System.out.println("6: Add Category");
		System.out.println("7: Cancel");
	}
	public void addSection()
	{
		int choice = getInt(1,7,sc);
		switch(choice)
		{
			case 1:
				addDebtSection();
				break;
			case 2:
				addUtilitySection();
				break;
			case 3:
				addIncomeSection();
				break;
			case 4:
				addDisposableTabSection();
				break;
			case 5:
				addProfileSection();
				break;
			case 6:
				addCategorySection();
				break;
			case 7:
				//
				break;
			default:
				break;
		}
	}
	public void showAddPayMenu()
	{
		System.out.println("====================");
		System.out.println("Add Pay Section");
		System.out.println("====================");
		System.out.println("1: Add Debt Payment");
		System.out.println("2: Add Utility Payment");
		System.out.println("3: Add Income Earning");
		System.out.println("4: Add Disposable Receipt");
		System.out.println("5: Cancel");
		
	}
	public void addPay()
	{
		int choice = getInt(1,5,sc);
		switch(choice)
		{
			case 1:
				addDebtPay();
				break;
			case 2:
				addUtilityPay();
				break;
			case 3:
				addIncomeEarning();
				break;
			case 4:
				addDisposableReceipt();
				break;
			case 5:
				//cancel
				break;
			default:
				break;
		}
		
	}
	public void showEditSectionMenu()
	{
		
	}	
	public void showEditPayMenu()
	{
		
	}	
	public void showViewTimespanMenu()
	{
		
	}
	
	
	
	public void viewMonthlyAccount()
	{
		double monthlyPayments = 0;
		double monthlyEarnings = 0;
		
		System.out.println("================");
		System.out.println("Viewing Account.");
		System.out.println(new Date());
		System.out.println("================\n");
		System.out.println("Debts:");
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
		System.out.println("Utilities:");
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
		System.out.println("Income:");
		for(BB_Earning income : account.income())
		{
			System.out.println(income.name() + ":");
			for(BB_Item payment : income.getEarnings(new Date()))
			{
				System.out.println("  " + payment.name() + " " + payment.costPerEach());
				monthlyEarnings += payment.costPerEach();
			}
		}
		System.out.println("Disposable Income:");
		for(BB_Disposable dispose : account.spendingTabs())
		{
			System.out.println(dispose.name() + ":");
			for(BB_Receipt receipt : dispose.getReceipts(new Date()))
			{
				System.out.println("$ " + receipt.name() + " " + receipt.total());
				monthlyPayments += receipt.total();
			}
		}
		System.out.println("Monthly Payments = " + monthlyPayments);
		System.out.println("Monthly Earnings = " + monthlyEarnings);
		System.out.println("Monthly Balance  = " + (monthlyEarnings - monthlyPayments));
	}

	//Add Sections
	//Add Section menu functions
	public void addDebtSection()
	{
		System.out.println("====================");
		System.out.println("Add Debt Section");
		System.out.println("====================");
		String name = "";
		double initialBalance = 0;
		double payment = 0;
		double principalPayment = 0;
		double interestPayment = 0;
		System.out.println("Enter the name of the debt.");
		name = sc.nextLine();
		System.out.println("Enter the initial balance.");
		initialBalance = getDouble(sc);
		System.out.println("Enter the payment amount.");
		payment = getDouble(sc);
		System.out.println("Enter the principal amount.");
		principalPayment = getDouble(sc);
		System.out.println("Enter the interest amount.");
		interestPayment = getDouble(sc);
		
		BB_Debt debt = new BB_Debt();
		debt.name(name);
		debt.initialBalance(initialBalance);
		debt.currentBalance(initialBalance);
		debt.payment(payment);
		debt.principalPayment(principalPayment);
		debt.interestPayment(interestPayment);
		account.addDebt(debt);
	}
	public void addUtilitySection()
	{
		System.out.println("====================");
		System.out.println("Add Utility Section");
		System.out.println("====================");
		String name = "";
		System.out.println("Enter the name of the Utility.");
		name = sc.nextLine();
		
		BB_Utility util = new BB_Utility();
		util.name(name);
		account.addUtility(util);
	}
	public void addIncomeSection()
	{
		System.out.println("====================");
		System.out.println("Add Income Section");
		System.out.println("====================");
		String name = "";
		System.out.println("Enter the name of the Income.");
		name = sc.nextLine();
		
		BB_Earning earn = new BB_Earning();
		earn.name(name);
		account.addEarning(earn);
	}
	public void addDisposableTabSection()
	{
		System.out.println("====================");
		System.out.println("Add Disposable Section");
		System.out.println("====================");
		String name = "";
		System.out.println("Enter the name of the Disposable Tab.");
		name = sc.nextLine();
		
		BB_Disposable disp = new BB_Disposable();
		disp.name(name);
		account.addDisposable(disp);
	}	
	public void addProfileSection()
	{
		System.out.println("====================");
		System.out.println("Add Profile Section");
		System.out.println("====================");
		String name = "";
		System.out.println("Enter the name of the Profile.");
		name = sc.nextLine();
		
		BB_Profile prof = new BB_Profile();
		prof.name(name);
		account.addProfile(prof);
	}	
	public void addCategorySection()
	{
		System.out.println("====================");
		System.out.println("Add Category Section");
		System.out.println("====================");
		String name = "";
		System.out.println("Enter the name of the Category.");
		name = sc.nextLine();
		
		BB_Category cat = new BB_Category();
		cat.name(name);
		account.addCategory(cat);
	}
	
	//Add Pay
	//Add Section menu functions
	public void addDebtPay()
	{
		System.out.println("====================");
		System.out.println("Add Debt Payment");
		System.out.println("====================");
		System.out.println("====================");
		System.out.println("Held Debts");
		for(BB_Debt debt : account.debts())
		{
			System.out.println("  " + debt.name());
		}
		String name = "";
		
		System.out.println("Enter the name of the debt being paid.");
		name = sc.nextLine();
		
		account.addDebtPayment(name);
	}
	public void addUtilityPay()
	{
		System.out.println("====================");
		System.out.println("Add Utility Payment");
		System.out.println("====================");
		System.out.println("====================");
		System.out.println("Held Utilities");
		for(BB_Utility util : account.utilities())
		{
			System.out.println("  " + util.name());
		}
		String name = "";
		double payment = 0;
		System.out.println("Enter the name of the Utility.");
		name = sc.nextLine();
		System.out.println("Enter the amount paid.");
		payment = this.getDouble(sc);
		
		account.addUtilityPayment(name, payment);
	}
	public void addIncomeEarning()
	{
		System.out.println("====================");
		System.out.println("Add Income Earning");
		System.out.println("====================");
		System.out.println("====================");
		System.out.println("Income Sections");
		for(BB_Earning earn : account.income())
		{
			System.out.println("  " + earn.name());
		}
		String name = "";
		double payment = 0;
		System.out.println("Enter the name of the Income.");
		name = sc.nextLine();
		System.out.println("Enter the amount earned.");
		payment = this.getDouble(sc);
		
		account.addIncomePayment(name, payment);
	}
	public void addDisposableReceipt()
	{
		System.out.println("====================");
		System.out.println("Add Disposable Receipt");
		System.out.println("====================");
		System.out.println("====================");
		System.out.println("Disposable Sections");
		for(BB_Disposable disp : account.spendingTabs())
		{
			System.out.println("  " + disp.name());
		}
		String name = "";
		System.out.println("Enter the name of the Disposable Tab.");
		name = sc.nextLine();
		
		//insert items into the receipt
	}	
	
	public int getInt(int min, int max, Scanner sc)
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
	public double getDouble(Scanner sc)
	{
		while(true)
		{
			if(sc.hasNextDouble())
				return sc.nextDouble();
			else
			{
				sc.nextLine();
				System.out.println("Please enter a valid Double-type number.");
			}
		}
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
