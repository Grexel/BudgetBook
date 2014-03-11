package BB_Client;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

import BB_Model.BB_Account;
import BB_Model.BB_Category;
import BB_Model.BB_Debt;
import BB_Model.BB_Disposable;
import BB_Model.BB_Earning;
import BB_Model.BB_FileStorage;
import BB_Model.BB_Item;
import BB_Model.BB_Profile;
import BB_Model.BB_Receipt;
import BB_Model.BB_Utility;

public class BB_ConsoleApplication {

	private Scanner sc;
	private BB_Account account;
	boolean quit = false;
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
		while(!quit)
		{
			showMainMenu();
			mainMenu();
			System.out.println("\nPress Enter to continue");
			sc.nextLine();
		}
	}
	
	public void showMainMenu()
	{
		System.out.println("Account: " + account.name());
		System.out.println("====================");
		System.out.println("1: View Monthly Log        10: View Timespan");
		System.out.println("2: Add D/U/I/S/P/C         11: Estimate Future Expenses");
		System.out.println("3: Add Payment/Earning     12: View Profiles and Categories");
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
	public void mainMenu()
	{
		int choice = getInt(1,12);
		switch(choice)
		{
			case 1:
				chooseMonthForViewing();
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
				saveAccount();
				break;
			case 7:
				loadAccount();
				break;
			case 8:
				newAccount();
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
			case 12:
				//
				viewPandC();
				break;
			default:
				break;
		}
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
		int choice = getInt(1,7);
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
		int choice = getInt(1,5);
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
	
	
	public void chooseMonthForViewing()
	{
		boolean validDate = false;
		Date dateToView = null;
		SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");
		
		do
		{
			System.out.println("Please enter date to view in (MM/yyyy) format.");
			String dateHolder = sc.nextLine();
			try{
				dateToView = format.parse(dateHolder);
				validDate = true;
			}catch(Exception e){e.printStackTrace();}
		}while(!validDate);
		
		viewMonthlyAccount(dateToView);
		
	}
	public void viewMonthlyAccount(Date dateUsed)
	{
		double monthlyPayments = 0;
		double monthlyEarnings = 0;
		double debtPayments = 0;
		double utilityPayments = 0;
		double disposablePayments = 0;
		
		
		System.out.println("================");
		System.out.println("Viewing Account.");
		System.out.println(dateUsed);
		System.out.println("================\n");
		System.out.println("Debts:");
		for(BB_Debt debt : account.debts())
		{
			debtPayments += debt.payment();	
			if(debt.isCurrent(dateUsed))
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
			System.out.println("  " + utility.name() + "  avg: " + utility.averagePayment());
			for(BB_Item payment : utility.getPayment(dateUsed))
			{
				System.out.println("    " + payment.name() + " " + payment.costPerEach());
				monthlyPayments += payment.costPerEach();
				utilityPayments += payment.costPerEach();
			}
		}
		System.out.println("Income:");
		for(BB_Earning income : account.income())
		{
			double totalIncome = 0;
			for(BB_Item payment : income.getEarnings(dateUsed))
			{
				totalIncome += payment.costPerEach();
			}
			System.out.println("  " + income.name() + ": " + totalIncome);
			for(BB_Item payment : income.getEarnings(dateUsed))
			{
				System.out.println("    " + payment.name() + " " + payment.costPerEach());
				monthlyEarnings += payment.costPerEach();
			}
		}
		System.out.println("Disposable Income:");
		for(BB_Disposable dispose : account.spendingTabs())
		{
			double totalDisp = 0;
			for(BB_Receipt receipt : dispose.getReceipts(dateUsed))
			{
				totalDisp += receipt.total();
			}
			System.out.println("  " + dispose.name() + ": " + totalDisp);
			for(BB_Receipt receipt : dispose.getReceipts(dateUsed))
			{
				System.out.println("   $" + receipt.name() + " " + receipt.total());
				monthlyPayments += receipt.total();
				disposablePayments += receipt.total();
			}
		}
		System.out.println("Total Debts = " + debtPayments);
		System.out.println("Utility Payments = " + utilityPayments);
		System.out.println("Disposable Payments  = " + disposablePayments);
		System.out.println("");
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
		initialBalance = getDouble();
		System.out.println("Enter the payment amount.");
		payment = getDouble();
		System.out.println("Enter the principal amount.");
		principalPayment = getDouble();
		System.out.println("Enter the interest amount.");
		interestPayment = getDouble();
		
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
		payment = this.getDouble();
		
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
		payment = this.getDouble();
		
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
		String dispName = "";
		String recName = "";
		double recTax = 0;
		ArrayList<BB_Item> receiptItems = new ArrayList<BB_Item>();
		System.out.println("Enter the name of the Disposable Tab.");
		dispName = sc.nextLine();
		System.out.println("Enter the name of the Receipt.");
		recName = sc.nextLine();
		System.out.println("Enter the receipt tax.");
		recTax = getDouble();
		
		boolean addItems = true;
		while(addItems)
		{
			inputItemForReceipt(receiptItems);
			System.out.println("Add another item to receipt? (y/n)");
			if(sc.nextLine().equals("n"))
			{
				addItems = false;
			}
		}
		account.getDisposable(dispName).addReceipt(account.generateReceipt(receiptItems, recName, recTax));
		//insert items into the receipt
	}	
	public void inputItemForReceipt(ArrayList<BB_Item> items)
	{
		//public BB_Item(int iNum, String name, int numItems, double costEach,
		//		Date date, BB_Profile attTo, BB_Category cat)
		String itemName = "";
		int numItems = 0;
		double costEach = 0;
		boolean addProfile = false;
		String profName = "";
		boolean addCategory = false;
		String catName = "";
		System.out.println("Enter the item name.");
		itemName = sc.nextLine();
		System.out.println("Enter the number of items.");
		numItems = getInt();
		System.out.println("Enter the item cost per each.");
		costEach = getDouble();
		System.out.println("Would you like to attach a profile to this item? (y/n)");
		if(sc.nextLine().equals("y"))
		{
			addProfile = true;
			System.out.println("Current Profiles");
			for(BB_Profile prof : account.profiles())
			{
				System.out.println(prof.name());
			}
			System.out.println("Enter Profile name.");
			profName = sc.nextLine();	
		}
		System.out.println("Would you like to attach a category to this item? (y/n)");
		if(sc.nextLine().equals("y"))
		{
			addCategory = true;
			System.out.println("Current Categories");
			for(BB_Category prof : account.categories())
			{
				System.out.println(prof.name());
			}
			System.out.println("Enter Category name.");
			catName = sc.nextLine();	
		}
		BB_Item i = account.generatePayment(itemName, numItems, costEach,
				account.getProfile(profName), account.getCategory(catName));
		System.out.println("Generated item : " + i.itemNumber() + " " + i.name() + " " + i.costPerEach());
		items.add(i);

	}
	public void saveAccount()
	{
		System.out.println("====================");
		System.out.println("Saving Account");
		System.out.println("====================");
		BB_FileStorage.saveAccount(account);
		System.out.println("Account Saved");
	}
	public void loadAccount()
	{
		String accountName = "";
		System.out.println("====================");
		System.out.println("Loading Account");
		System.out.println("====================");
		File f = new File(BB_FileStorage.defaultDirectory);
		if(f.exists())
		{
			if(f.isDirectory())
			{
				System.out.println("Current Accounts:");
				for(File df : f.listFiles())
				{
					System.out.println(df.getName());
				}
			}
		}
		System.out.print("Enter the name of the account: ");
		accountName = sc.nextLine();
		account = BB_FileStorage.loadAccount(accountName);
		System.out.print("Account " + accountName + " loaded.");
	}
	public void newAccount()
	{
		String accountName = "";
		System.out.println("====================");
		System.out.println("New Account");
		System.out.println("====================");
		System.out.print("Enter the name of the account: ");
		accountName = sc.nextLine();
		account = new BB_Account();
		account.name(accountName);
	
	}
	
	public void viewPandC()
	{
		for(BB_Profile p : account.profiles())
		{
			System.out.println("Profile: " + p.name());
		}
		for(BB_Category p : account.categories())
		{
			System.out.println("Category: " + p.name());
		}
	}

	public int getInt(int min, int max)
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
	public int getInt()
	{
		boolean inputCorrect = false;
		int inputInt = 0;
		String inputString = "";
		while(!inputCorrect)
		{
			inputString = sc.nextLine();
			try{
				inputInt = Integer.parseInt(inputString);
				inputCorrect = true;
			}catch(Exception e){ System.out.println("Please enter a valid integer");}
		}
		return inputInt;
	}
	public double getDouble()
	{
		while(true)
		{
			if(sc.hasNextDouble())
			{
				double d = sc.nextDouble();
				sc.nextLine();
				return d;
			}
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
	
}
