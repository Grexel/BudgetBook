package BB_Model;
import java.util.ArrayList;


public class BB_Profile {
	private String _name;

	public BB_Profile()
	{
		name("Default");
	}
	public BB_Profile(String name)
	{
		name(name);
	}
	public String name(){ return _name;}
	public void name(String x){_name = x;}
}
