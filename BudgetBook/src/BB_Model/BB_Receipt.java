package BB_Model;
import java.util.ArrayList;
import java.util.Date;


public class BB_Receipt
{
	private int _receiptNumber;
	private String _name;
	private Date _date;
	private ArrayList<BB_Item> _listOfItems;
	private double _tax;

	public BB_Receipt(int num, String name, Date date, double tax)
	{
		receiptNumber(num);
		name(name);
		date(date);
		listOfItems(new ArrayList<BB_Item>());
		tax(tax);
	}
	public BB_Receipt(int num, String name, Date date, ArrayList<BB_Item> list, double tax)
	{
		receiptNumber(num);
		name(name);
		date(date);
		listOfItems(list);
		tax(tax);
	}

	public double total()
	{
		double total = tax();
		for(BB_Item item : listOfItems())
		{
			total += item.numberOfItems() * item.costPerEach();
		}
		return total;
	}
	public void addItem(BB_Item item)
	{
		listOfItems().add(item);
	}
	public void removeItem(int itemNum)
	{
		for(BB_Item item : listOfItems())
		{
			if(item.itemNumber() == itemNum)
			listOfItems().remove(item);
		}
	}
	public void editItem(int itemNum, String name, int numItems, double costEach,
		Date date, BB_Profile attTo, BB_Category cat)
	{
		for(BB_Item item : listOfItems())
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
	//Get Set Functions
	public int receiptNumber(){ return _receiptNumber;}
	public void receiptNumber(int x){_receiptNumber = x;}
	public String name(){ return _name;}
	public void name(String x){_name = x;}
	public Date date(){ return _date;}
	public void date(Date x){_date = x;}
	public ArrayList<BB_Item> listOfItems(){ return _listOfItems;}
	public void listOfItems(ArrayList<BB_Item> x){_listOfItems = x;}
	public double tax(){ return _tax;}
	public void tax(double x){_tax = x;}
}