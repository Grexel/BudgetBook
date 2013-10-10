import java.util.*;
public class BB_Account
{
	private double _balance;
	private int _nextItemNumber;

	private ArrayList<BB_Debt> _debts;
	private ArrayList<BB_Utility> _utilities;
	private ArrayList<BB_Disposable> _receipts;
	private ArrayList<BB_Earning> _income;

	private ArrayList<BB_Profile> _persons;
	private ArrayList<BB_Category> _categories;

	public BB_Account()
	{
		balance(0);
		nextItemNumber(0);
		debts(new ArrayList<BB_Debt>());
		utilities(new ArrayList<BB_Utility>());
		receipts(new ArrayList<BB_Disposable>());
		income(new ArrayList<BB_Earning>());

		persons(new ArrayList<BB_Profile>());
		categories(new ArrayList<BB_Category>());
	}
	public void addDebt(BB_Debt dbt)
	{
		debts().add(dbt);
	}

	//Get and Set Functions
	public double balance() {return _balance;}
	public void balance(double balance) {_balance = balance;}
	public int nextItemNumber() {return _nextItemNumber;}
	public void nextItemNumber(int nextItemNumber) {_nextItemNumber = nextItemNumber;}
	public ArrayList<BB_Debt> debts() {return _debts;}
	public void debts(ArrayList<BB_Debt> debts) {_debts = debts;}
	public ArrayList<BB_Utility> utilities() {return _utilities;}
	public void utilities(ArrayList<BB_Utility> utilities) {_utilities = utilities;}
	public ArrayList<BB_Disposable> receipts() {return _receipts;}
	public void receipts(ArrayList<BB_Disposable> receipts) {_receipts = receipts;}
	public ArrayList<BB_Earning> income() {return _income;}
	public void income(ArrayList<BB_Earning> income) {_income = income;}
	public ArrayList<BB_Profile> persons() {return _persons;}
	public void persons(ArrayList<BB_Profile> persons) {_persons = persons;}
	public ArrayList<BB_Category> categories() {return _categories;}
	public void categories(ArrayList<BB_Category> categories) {_categories = categories;}
}