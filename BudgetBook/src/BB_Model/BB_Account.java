package BB_Model;
import java.util.*;
public class BB_Account
{
	private String _name;
	private double _balance;
	private int _nextItemNumber;
	private int _nextReceiptNumber;

	private ArrayList<BB_Debt> _debts;
	private ArrayList<BB_Utility> _utilities;
	private ArrayList<BB_Disposable> _spendingTabs;
	private ArrayList<BB_Earning> _income;

	private ArrayList<BB_Profile> _profiles;
	private ArrayList<BB_Category> _categories;

	public BB_Account()
	{
		name("default");
		balance(0);
		nextItemNumber(0);
		nextReceiptNumber(0);
		debts(new ArrayList<BB_Debt>());
		utilities(new ArrayList<BB_Utility>());
		spendingTabs(new ArrayList<BB_Disposable>());
		income(new ArrayList<BB_Earning>());

		profiles(new ArrayList<BB_Profile>());
		categories(new ArrayList<BB_Category>());
		addProfile(new BB_Profile("Default"));
		addCategory(new BB_Category("Default"));
	}
	public BB_Account(String name)
	{
		System.out.println("Account Created " + name);
		name(name);
		balance(0);
		nextItemNumber(0);
		nextReceiptNumber(0);
		debts(new ArrayList<BB_Debt>());
		utilities(new ArrayList<BB_Utility>());
		spendingTabs(new ArrayList<BB_Disposable>());
		income(new ArrayList<BB_Earning>());

		profiles(new ArrayList<BB_Profile>());
		categories(new ArrayList<BB_Category>());
	}
	
	//Add functions
	public void addDebt(BB_Debt dbt)
	{
		for(BB_Debt debt : debts())
		{
			if(debt.name().equals(dbt.name()))
				return;
		}
		debts().add(dbt);
	}
	public void addUtility(BB_Utility util)
	{
		for(BB_Utility debt : utilities())
		{
			if(debt.name().equals(util.name()))
				return;
		}
		utilities().add(util);
	}
	public void addDisposable(BB_Disposable tab)
	{
		for(BB_Disposable debt : spendingTabs())
		{
			if(debt.name().equals(tab.name()))
				return;
		}
		spendingTabs().add(tab);
	}
	public void addEarning(BB_Earning earn)
	{
		for(BB_Earning debt : income())
		{
			if(debt.name().equals(earn.name()))
				return;
		}
		income().add(earn);
	}
	public void addProfile(BB_Profile prof)
	{
		for(BB_Profile debt : profiles())
		{
			if(debt.name().equals(prof.name()))
				return;
		}
		profiles().add(prof);
	}
	public void addCategory(BB_Category cat)
	{
		for(BB_Category debt : categories())
		{
			if(debt.name().equals(cat.name()))
				return;
		}
		categories().add(cat);
	}
	
	//Add to functions
	public void addDebtPayment(String debtName)
	{
		for(BB_Debt debt : debts())
		{
			if(debt.name().equals(debtName))
			{
				debt.addPayment(
						generatePayment(debt.name() + "_Payment",1,debt.payment()));
			}
			else
			{
				//add error code
			}
		}
	}
	public void addUtilityPayment(String utilName, double cost)
	{
		for(BB_Utility util : utilities())
		{
			if(util.name().equals(utilName))
			{
				util.addPayment(
						generatePayment(util.name() + "_Payment",1,cost));
			}
		}
	}
	public void addDisposablePayment(String dispName, BB_Receipt rec)
	{
		for(BB_Disposable group : spendingTabs())
		{
			if(group.name().equals(dispName))
			{
				group.addReceipt(rec);
			}
			else
			{
				//add error code
			}
		}
	}
	public void addIncomePayment(String incName, double amountEarned)
	{
		for(BB_Earning earning : income())
		{
			if(earning.name().equals(incName))
			{
				earning.addPayment(
						generatePayment(earning.name() + "_Payment",1,amountEarned));
			}
			else
			{
				//add error code
			}
		}
	}
	
	public BB_Receipt generateReceipt(ArrayList<BB_Item> items, String name, double tax)
	{
		BB_Receipt receipt = new BB_Receipt(nextReceiptNumber(),name, new Date(), items,  tax);
		return receipt;
	}
	public BB_Item generatePayment(String name, int numItems, double cost)
	{
		BB_Item payment = new BB_Item(nextItemNumber(), name,
				numItems, cost, new Date(), getProfile("Default"), getCategory("Default"));
		return payment;
	}	
	public BB_Item generatePayment(String name, int numItems, double cost, BB_Profile profile, BB_Category cat)
	{
		if(profile == null) profile = getProfile("Default");
		if(cat == null) cat = getCategory("Default");
		BB_Item payment = new BB_Item(nextItemNumber(), name,
				numItems, cost, new Date(), profile, cat);
		return payment;
	}
	
	
	
	
	public BB_Debt getDebt(String name)
	{
		for(BB_Debt prof : this.debts())
		{
			if(prof.name().equals(name))
			{
				return prof;
			}
		}
		return null;
	}	
	public BB_Utility getUtility(String name)
	{
		for(BB_Utility prof : this.utilities())
		{
			if(prof.name().equals(name))
			{
				return prof;
			}
		}
		return null;
	}	
	public BB_Disposable getDisposable(String name)
	{
		for(BB_Disposable prof : this.spendingTabs())
		{
			if(prof.name().equals(name))
			{
				return prof;
			}
		}
		return null;
	}	
	public BB_Earning getIncome(String name)
	{
		for(BB_Earning prof : this.income())
		{
			if(prof.name().equals(name))
			{
				return prof;
			}
		}
		return null;
	}	
	public BB_Profile getProfile(String name)
	{
		for(BB_Profile prof : this.profiles())
		{
			if(prof.name().equals(name))
			{
				System.out.println("Found Profile: " + prof.name());
				return prof;
			}
		}
		System.out.println("Did not find Profile: " + name);
		return null;
	}
	public BB_Category getCategory(String name)
	{
		for(BB_Category prof : this.categories())
		{
			if(prof.name().equals(name))
			{
				return prof;
			}
		}
		return null;
	}
	//Get and Set Functions
	public String name(){ return _name;}
	public void name(String x){_name = x;}
	public double balance() {return _balance;}
	public void balance(double balance) {_balance = balance;}
	public int currentItemNumber() {return _nextItemNumber;}
	public int nextItemNumber() {return _nextItemNumber++;}
	public void nextItemNumber(int nextItemNumber) {_nextItemNumber = nextItemNumber;}
	public int currentReceiptNumber() {return _nextReceiptNumber;}
	public int nextReceiptNumber() {return _nextReceiptNumber++;}
	public void nextReceiptNumber(int nextReceiptNumber) {_nextReceiptNumber = nextReceiptNumber;}
	public ArrayList<BB_Debt> debts() {return _debts;}
	public void debts(ArrayList<BB_Debt> debts) {_debts = debts;}
	public ArrayList<BB_Utility> utilities() {return _utilities;}
	public void utilities(ArrayList<BB_Utility> utilities) {_utilities = utilities;}
	public ArrayList<BB_Disposable> spendingTabs() {return _spendingTabs;}
	public void spendingTabs(ArrayList<BB_Disposable> receipts) {_spendingTabs = receipts;}
	public ArrayList<BB_Earning> income() {return _income;}
	public void income(ArrayList<BB_Earning> income) {_income = income;}
	public ArrayList<BB_Profile> profiles() {return _profiles;}
	public void profiles(ArrayList<BB_Profile> persons) {_profiles = persons;}
	public ArrayList<BB_Category> categories() {return _categories;}
	public void categories(ArrayList<BB_Category> categories) {_categories = categories;}
}