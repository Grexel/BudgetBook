import java.util.*;
public class BB_Account
{
	private String _name;
	private double _balance;
	private int _nextItemNumber;

	private ArrayList<BB_Debt> _debts;
	private ArrayList<BB_Utility> _utilities;
	private ArrayList<BB_Disposable> _spendingTabs;
	private ArrayList<BB_Earning> _income;

	private ArrayList<BB_Profile> _profiles;
	private ArrayList<BB_Category> _categories;

	public BB_Account()
	{
		balance(0);
		nextItemNumber(0);
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
		debts().add(dbt);
	}
	public void addUtility(BB_Utility util)
	{
		utilities().add(util);
	}
	public void addDisposable(BB_Disposable tab)
	{
		spendingTabs().add(tab);
	}
	public void addEarning(BB_Earning earn)
	{
		income().add(earn);
	}
	public void addProfile(BB_Profile prof)
	{
		profiles().add(prof);
	}
	public void addCategory(BB_Category cat)
	{
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
		BB_Receipt receipt = new BB_Receipt(nextItemNumber(),name, new Date(), items,  tax);
		return receipt;
	}
	public BB_Item generatePayment(String name, int numItems, double cost)
	{
		BB_Item payment = new BB_Item(nextItemNumber(), name,
				numItems, cost, new Date(), null, null);
		return payment;
	}
	
	//Get and Set Functions
	public String name(){ return _name;}
	public void name(String x){_name = x;}
	public double balance() {return _balance;}
	public void balance(double balance) {_balance = balance;}
	public int nextItemNumber() {return _nextItemNumber++;}
	public void nextItemNumber(int nextItemNumber) {_nextItemNumber = nextItemNumber;}
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