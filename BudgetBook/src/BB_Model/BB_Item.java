package BB_Model;
import java.util.Date;


public class BB_Item
{
	private int _itemNumber;
	private String _name;
	private int _numberOfItems;
	private double _costPerEach;
	private Date _date;
	private BB_Profile _attributedTo;
	private BB_Category _category;

	//constructor
	public BB_Item()
	{
		itemNumber(-1);
		name("default");
		numberOfItems(0);
		costPerEach(0);
		date(new Date());
		attributedTo(null);
		category(null);

	}
	public BB_Item(int iNum, String name, int numItems, double costEach,
		Date date, BB_Profile attTo, BB_Category cat)
	{
		itemNumber(iNum);
		name(name);
		numberOfItems(numItems);
		costPerEach(costEach);
		date(date);
		attributedTo(attTo);
		category(cat);
	}
	//Get and Set functions
	public int itemNumber(){ return _itemNumber;}
	public void itemNumber(int x){_itemNumber = x;}
	public String name(){ return _name;}
	public void name(String x){_name = x;}
	public int numberOfItems(){ return _numberOfItems;}
	public void numberOfItems(int x){_numberOfItems = x;}
	public double costPerEach(){ return _costPerEach;}
	public void costPerEach(double x){_costPerEach = x;}
	public Date date(){ return _date;}
	public void date(Date x){_date = x;}
	public BB_Profile attributedTo(){ return _attributedTo;}
	public void attributedTo(BB_Profile x){_attributedTo = x;}
	public BB_Category category(){ return _category;}
	public void category(BB_Category x){_category = x;}
}