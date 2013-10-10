import java.util.ArrayList;
import java.util.Date;

public class BB_Debt
{
	private String _name;

	private double _initialBalance;
	private double _currentBalance;

	private double _payment;
	private double _principalPayment;
	private double _interestPayment;
	private ArrayList<BB_Item> _listOfPayments;

	//Get Set Functions
	public String name(){ return _name;}
	public void name(String x){_name = x;}
	public ArrayList<BB_Item> listOfPayments(){ return _listOfPayments;}
	public void listOfPayments(ArrayList<BB_Item> x){_listOfPayments = x;}
	public double initialBalance(){ return _initialBalance;}
	public void initialBalance(double x){_initialBalance = x;}
	public double currentBalance(){ return _currentBalance;}
	public void currentBalance(double x){_currentBalance = x;}
	public double payment(){ return _payment;}
	public void payment(double x){_payment = x;}
	public double principalPayment(){ return _principalPayment;}
	public void principalPayment(double x){_principalPayment = x;}
	public double interestPayment(){ return _interestPayment;}
	public void interestPayment(double x){_interestPayment = x;}

	public void getPayments(Date begin, Date end)
	{
		
	}
	public void addPayment(BB_Item item)
	{
		listOfPayments().add(item);
	}
	public void removePayment(int itemNum)
	{
		for(BB_Item item : listOfPayments())
		{
			if(item.itemNumber() == itemNum)
			listOfPayments().remove(item);
		}
	}
	public void editPayment(int itemNum, String name, int numItems, double costEach,
		Date date, BB_Profile attTo, BB_Category cat)
	{
		for(BB_Item item : listOfPayments())
		{
			if(item.itemNumber() == itemNum)
			{
				item.name(name);
				item.numberOfItems(numItems);
				item.costPerEach(costEach);
				item.date(date);
				item.attributedTo(attTo);
				item.category(cat);
			}
		}
	}
}