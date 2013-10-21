import java.util.*;
public class BB_Debt
{
	private String _name;

	private double _initialBalance;
	private double _currentBalance;

	private double _payment;
	private double _principalPayment;
	private double _interestPayment;
	private ArrayList<BB_Item> _listOfPayments;

	public BB_Debt()
	{
		name("default");
		listOfPayments(new ArrayList<BB_Item>());
		initialBalance(0);
		currentBalance(0);
		payment(0);
		principalPayment(0);
		interestPayment(0);
	}
	public BB_Debt(String name)
	{
		name(name);
		listOfPayments(new ArrayList<BB_Item>());
		initialBalance(0);
		currentBalance(0);
		payment(0);
		principalPayment(0);
		interestPayment(0);
	}
	public BB_Debt(String name, double intBal, double curBal, double payment, double princePay, double intPay)
	{
		name(name);
		listOfPayments(new ArrayList<BB_Item>());
		initialBalance(intBal);
		currentBalance(curBal);
		payment(payment);
		principalPayment(princePay);
		interestPayment(intPay);
	}
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

	public ArrayList<BB_Item> getPayments(Date start, Date end)
	{
		ArrayList<BB_Item> receipts = new ArrayList<BB_Item>();
		
		Calendar startDate = new GregorianCalendar();
		startDate.setTime(start);
		Calendar endDate = new GregorianCalendar();
		startDate.setTime(end);
		Calendar paymentDate = new GregorianCalendar();
		
		for(BB_Item payment : listOfPayments())
		{
			paymentDate.setTime(payment.date());
			if(startDate.before(paymentDate) && endDate.after(paymentDate))
			{
				receipts.add(payment);
			}
		}
		return receipts;
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
	public boolean isCurrent()
	{
		Calendar currentDate = new GregorianCalendar();
		Calendar paymentDate = new GregorianCalendar();
		for(BB_Item payment : listOfPayments())
		{
			paymentDate.setTime(payment.date());
			if(currentDate.get(Calendar.MONTH) == paymentDate.get(Calendar.MONTH) &&
					currentDate.get(Calendar.YEAR) == paymentDate.get(Calendar.YEAR))
			{
				return true;
			}
		}
		return false;
	}

}