import java.util.ArrayList;
import java.util.Date;


public class BB_Disposable
{
	private String _name;
	private ArrayList<BB_Receipt> _listOfPayments;

	//Get Set Functions
	public String name(){ return _name;}
	public void name(String x){_name = x;}
	public ArrayList<BB_Receipt> listOfPayments(){ return _listOfPayments;}
	public void listOfPayments(ArrayList<BB_Receipt> x){_listOfPayments = x;}
}