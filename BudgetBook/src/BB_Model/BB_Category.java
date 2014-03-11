package BB_Model;
import java.util.ArrayList;


public class BB_Category {

	private String _name;

	public BB_Category()
	{
		name("Default");
	}
	public BB_Category(String name)
	{
		name(name);
	}
	public String name(){ return _name;}
	public void name(String x){_name = x;}
}
