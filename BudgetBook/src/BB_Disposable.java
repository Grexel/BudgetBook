import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class BB_Disposable
{
	private String _name;
	private ArrayList<BB_Receipt> _listOfPayments;

	public BB_Disposable()
	{
		name("Default");
		listOfPayments(new ArrayList<BB_Receipt>());
	}
	//add to function
	public ArrayList<BB_Receipt> getReceipts(Date month)
	{
		ArrayList<BB_Receipt> receipts = new ArrayList<BB_Receipt>();
		Calendar lookupDate = new GregorianCalendar();
		lookupDate.setTime(month);
		Calendar paymentDate = new GregorianCalendar();
		for(BB_Receipt payment : listOfPayments())
		{
			paymentDate.setTime(payment.date());
			if(lookupDate.get(Calendar.MONTH) == paymentDate.get(Calendar.MONTH) &&
					lookupDate.get(Calendar.YEAR) == paymentDate.get(Calendar.YEAR))
			{
				receipts.add(payment);
			}
		}
		return receipts;
	}
	public ArrayList<BB_Receipt> getReceipts(Date start, Date end)
	{
		ArrayList<BB_Receipt> receipts = new ArrayList<BB_Receipt>();
		
		Calendar startDate = new GregorianCalendar();
		startDate.setTime(start);
		Calendar endDate = new GregorianCalendar();
		startDate.setTime(end);
		Calendar paymentDate = new GregorianCalendar();
		
		for(BB_Receipt payment : listOfPayments())
		{
			paymentDate.setTime(payment.date());
			if(startDate.before(paymentDate) && endDate.after(paymentDate))
			{
				receipts.add(payment);
			}
		}
		return receipts;
	}
	public void addReceipt(BB_Receipt rec)
	{
		listOfPayments().add(rec);
	}
	//Get Set Functions
	public String name(){ return _name;}
	public void name(String x){_name = x;}
	public ArrayList<BB_Receipt> listOfPayments(){ return _listOfPayments;}
	public void listOfPayments(ArrayList<BB_Receipt> x){_listOfPayments = x;}
}