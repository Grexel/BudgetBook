import java.io.*;
import java.io.File;


public class BB_FileStorage {

	public static String defaultDirectory = "AccountFiles" + File.pathSeparator;
	
	public static BB_Account loadAccount(String accountName)
	{
		try (BufferedReader br = new BufferedReader(new FileReader(new File(defaultDirectory + accountName))))
		{
			BB_Account account = new BB_Account();
		
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null)
			{
				String[] commands = sCurrentLine.split(":");
				if(commands[0].equals("Name"))
				{
					account.name(commands[1]);
				}
				if(commands[0].equals("Debt"))
				{
					BB_Debt debt = new BB_Debt();
					debt.name(commands[1]);
					account.addDebt(debt);
				}
				if(commands[0].equals("Utility"))
				{
					BB_Utility util = new BB_Utility();
					util.name(commands[1]);
					account.addUtility(util);
				}
				if(commands[0].equals("Income"))
				{
					BB_Earning util = new BB_Earning();
					util.name(commands[1]);
					account.addEarning(util);
				}
				if(commands[0].equals("Disposable"))
				{
					BB_Disposable util = new BB_Disposable();
					util.name(commands[1]);
					account.addDisposable(util);
				}
				if(commands[0].equals("Profile"))
				{
					BB_Profile util = new BB_Profile();
					util.name(commands[1]);
					account.addProfile(util);
				}
				if(commands[0].equals("Category"))
				{
					BB_Category util = new BB_Category();
					util.name(commands[1]);
					account.addCategory(util);
				}
				if(commands[0].equals("CategoryItem"))
				{
				}
				if(commands[0].equals("T"))
				{
				}
				//System.out.println(sCurrentLine);
				return account;
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	return null;
	}
	
	public static void saveAccount(BB_Account account)
	{
		
	}
}
