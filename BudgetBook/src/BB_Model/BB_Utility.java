package BB_Model;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class BB_Utility
{
	private String _name;
	private ArrayList<BB_Item> _listOfPayments;

	public BB_Utility()
	{
		name("Default");
		listOfPayments(new ArrayList<BB_Item>());
	}
	public BB_Utility(String name)
	{
		name(name);
		listOfPayments(new ArrayList<BB_Item>());
	}
	//Get Set Functions
	public String name(){ return _name;}
	public void name(String x){_name = x;}
	public ArrayList<BB_Item> listOfPayments(){ return _listOfPayments;}
	public void listOfPayments(ArrayList<BB_Item> x){_listOfPayments = x;}

	public ArrayList<BB_Item> getPayment(Date month)
	{
		ArrayList<BB_Item> earnings = new ArrayList<BB_Item>();
		Calendar lookupDate = new GregorianCalendar();
		lookupDate.setTime(month);
		Calendar paymentDate = new GregorianCalendar();
		for(BB_Item payment : listOfPayments())
		{
			paymentDate.setTime(payment.date());
			if(lookupDate.get(Calendar.MONTH) == paymentDate.get(Calendar.MONTH) &&
					lookupDate.get(Calendar.YEAR) == paymentDate.get(Calendar.YEAR))
			{
				earnings.add(payment);
			}
		}
		return earnings;
	}
	public ArrayList<BB_Item> getPayments(Date start, Date end)
	{
		ArrayList<BB_Item> receipts = new ArrayList<BB_Item>();
		
		Calendar startDate = new GregorianCalendar();
		startDate.setTime(start);
		Calendar endDate = new GregorianCalendar();
		endDate.setTime(end);
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
	
	public double averagePayment()
	{
		int numOfPayments = 0;
		double totalCost = 0;
		for(BB_Item payment : listOfPayments())
		{
			numOfPayments++;
			totalCost += (payment.numberOfItems() * payment.costPerEach());
		}
		return totalCost / numOfPayments;
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