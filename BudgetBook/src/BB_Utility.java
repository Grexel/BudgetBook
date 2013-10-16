import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class BB_Utility
{
	private String _name;
	private ArrayList<BB_Item> _listOfPayments;

	//Get Set Functions
	public String name(){ return _name;}
	public void name(String x){_name = x;}
	public ArrayList<BB_Item> listOfPayments(){ return _listOfPayments;}
	public void listOfPayments(ArrayList<BB_Item> x){_listOfPayments = x;}

	public BB_Item getPayment(Date d)
	{
		Calendar lookupDate = new GregorianCalendar();
		lookupDate.setTime(d);
		Calendar paymentDate = new GregorianCalendar();
		for(BB_Item payment : listOfPayments())
		{
			paymentDate.setTime(payment.date());
			if(lookupDate.get(Calendar.MONTH) == paymentDate.get(Calendar.MONTH) &&
					lookupDate.get(Calendar.YEAR) == paymentDate.get(Calendar.YEAR))
			{
				return payment;
			}
		}
		return null;
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