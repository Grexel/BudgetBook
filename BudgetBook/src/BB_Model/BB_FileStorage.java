package BB_Model;
import java.io.*;
import java.util.Date;
import java.text.*;


public class BB_FileStorage {

	public static String defaultDirectory = "AccountFiles" + File.separator;
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	public static BB_Account loadAccount(String accountName)
	{
		File dir = new File("AccountFiles");
		if(!dir.exists())
		{
			dir.mkdir();
		}
		return loadAccount(new File(defaultDirectory + accountName));
		
	}
	public static BB_Account loadAccount(File f)
	{
		try (BufferedReader br = new BufferedReader(new FileReader(f)))
		{
			BB_Account account = new BB_Account();
			
			String sCurrentLine;
			while (br.ready())
			{
				sCurrentLine = br.readLine();
				//System.out.println(sCurrentLine);
				String[] commands = sCurrentLine.split(":");
				if(commands[0].equals("Name"))
				{
					account.name(commands[1]);
					account.balance(Double.parseDouble(commands[2]));
					account.nextItemNumber(Integer.parseInt(commands[3]));
					account.nextReceiptNumber(Integer.parseInt(commands[4]));
					try{
						account.dateCreated(dateFormat.parse(commands[5]));
					}catch(Exception e){}
				}
				else if(commands[0].equals("Debt"))
				{
					BB_Debt debt = new BB_Debt(commands[1],
							Double.parseDouble(commands[2]),
							Double.parseDouble(commands[3]),
							Double.parseDouble(commands[4]),
							Double.parseDouble(commands[5]),
							Double.parseDouble(commands[6]),
							Boolean.parseBoolean(commands[7]));
					account.addDebt(debt);
//public BB_Debt(String name, double intBal, double curBal, double payment, double princePay, double intPay)
					
				}
				else if(commands[0].equals("Utility"))
				{
					BB_Utility util = new BB_Utility(commands[1]);
					account.addUtility(util);
				}
				else if(commands[0].equals("Income"))
				{
					BB_Earning util = new BB_Earning(commands[1]);
					account.addEarning(util);
				}
				else if(commands[0].equals("Disposable"))
				{
					BB_Disposable util = new BB_Disposable(commands[1]);
					account.addDisposable(util);
				}
				else if(commands[0].equals("Profile"))
				{
					BB_Profile util = new BB_Profile(commands[1]);
					account.addProfile(util);
				}
				else if(commands[0].equals("Category"))
				{
					BB_Category util = new BB_Category(commands[1]);
					account.addCategory(util);
				}
				else if(commands[0].equals("DP"))
				{
					//Name of Debt
					String debtName = commands[1];
					BB_Item item = createItem(account,commands[2],
							commands[3],commands[4],commands[5],
							commands[6],commands[7],commands[8]);
					for(BB_Debt debt : account.debts())
					{
						if(debt.name().equals(debtName))
						{
							debt.addPayment(item);
						}
					}
				}
				else if(commands[0].equals("UP"))
				{
					//Name of Debt
					String utilName = commands[1];
					BB_Item item = createItem(account,commands[2],
							commands[3],commands[4],commands[5],
							commands[6],commands[7],commands[8]);
					for(BB_Utility util : account.utilities())
					{
						if(util.name().equals(utilName))
						{
							util.addPayment(item);
						}
					}
				}
				else if(commands[0].equals("IE"))
				{
					//Name of Debt
					String utilName = commands[1];
					BB_Item item = createItem(account,commands[2],
							commands[3],commands[4],commands[5],
							commands[6],commands[7],commands[8]);
					for(BB_Earning util : account.income())
					{
						if(util.name().equals(utilName))
						{
							util.addPayment(item);
						}
					}
				}
				else if(commands[0].equals("DR"))
				{
					String dispName = commands[1];
					int recNum = Integer.parseInt(commands[2]);
					String recName = commands[3];
					Date recDate = new Date();
					try{
						recDate = dateFormat.parse(commands[4]);
					}catch(Exception e){}
					double tax = Double.parseDouble(commands[5]);
					for(BB_Disposable util : account.spendingTabs())
					{
						if(util.name().equals(dispName))
						{
							util.addReceipt(new BB_Receipt(recNum,recName,recDate,tax));
						}
					}
					//public BB_Receipt(int num, String name, Date date, double tax)
				}
				else if(commands[0].equals("DRI"))
				{
					String dispName = commands[1];
					int recNum = Integer.parseInt(commands[2]);
					BB_Item item = createItem(account,commands[3],
							commands[4],commands[5],commands[6],
							commands[7],commands[8],commands[9]);
					for(BB_Disposable util : account.spendingTabs())
					{
						if(util.name().equals(dispName))
						{
							for(BB_Receipt r : util.listOfPayments())
							{
								if(r.receiptNumber() == recNum)
									r.addItem(item);
							}
						}
					}
					//public BB_Receipt(int num, String name, Date date, double tax)
				}
			}
			br.close();
			return account;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	return null;
	}

	public static void saveAccount(BB_Account account, File file)
	{
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write("Name:" + account.name() + ":" +
					account.balance() + ":" +
					account.currentItemNumber() + ":" +
					account.currentReceiptNumber() + ":" +
					dateFormat.format(account.dateCreated())); bw.newLine();
			for(BB_Debt debt : account.debts())
			{
				bw.write("Debt:" + debt.name() + ":" + debt.initialBalance() + ":" + debt.currentBalance()
						+ ":" + debt.payment() + ":" + debt.principalPayment() + ":" + debt.interestPayment()
						+ ":" + debt.isClosed());
				bw.newLine();
			}
			for(BB_Utility util : account.utilities())
			{
				bw.write("Utility:" + util.name());
				bw.newLine();
			}
			for(BB_Earning util : account.income())
			{
				bw.write("Income:" + util.name());
				bw.newLine();
			}
			for(BB_Disposable util : account.spendingTabs())
			{
				bw.write("Disposable:" + util.name());
				bw.newLine();
			}
			for(BB_Profile util : account.profiles())
			{
				bw.write("Profile:" + util.name());
				bw.newLine();
			}
			for(BB_Category util : account.categories())
			{
				bw.write("Category:" + util.name());
				bw.newLine();
			}
			for(BB_Debt debt : account.debts())
			{
				for(BB_Item p : debt.listOfPayments())
				{
					bw.write("DP:" + debt.name() + ":" + p.itemNumber() + ":"
							+ p.name() + ":"
							+ p.numberOfItems() + ":"
							+ p.costPerEach() + ":"
							+ dateFormat.format(p.date()) + ":"
							+ p.attributedTo().name() + ":"
							+ p.category().name());
					bw.newLine();
				}
			}
			for(BB_Utility debt : account.utilities())
			{
				for(BB_Item p : debt.listOfPayments())
				{
					bw.write("UP:" + debt.name() + ":" + p.itemNumber() + ":"
							+ p.name() + ":"
							+ p.numberOfItems() + ":"
							+ p.costPerEach() + ":"
							+ dateFormat.format(p.date()) + ":"
							+ p.attributedTo().name() + ":"
							+ p.category().name());
					bw.newLine();
				}
			}
			for(BB_Earning debt : account.income())
			{
				for(BB_Item p : debt.listOfPayments())
				{
					bw.write("IE:" + debt.name() + ":" + p.itemNumber() + ":"
							+ p.name() + ":"
							+ p.numberOfItems() + ":"
							+ p.costPerEach() + ":"
							+ dateFormat.format(p.date()) + ":"
							+ p.attributedTo().name() + ":"
							+ p.category().name());
					bw.newLine();
				}
			}
			for(BB_Disposable debt : account.spendingTabs())
			{
				for(BB_Receipt p : debt.listOfPayments())
				{
					bw.write("DR:" + debt.name() + ":" + p.receiptNumber() + ":"
							+ p.name() + ":"
							+ dateFormat.format(p.date()) + ":"
							+ p.tax());
					bw.newLine();
				}
			}
			for(BB_Disposable debt : account.spendingTabs())
			{
				for(BB_Receipt p : debt.listOfPayments())
				{
					for(BB_Item item : p.listOfItems())
					{
						bw.write("DRI:" + debt.name() + ":" + p.receiptNumber() + ":"
								+ item.itemNumber() + ":"
								+ item.name() + ":"
								+ item.numberOfItems() + ":"
								+ item.costPerEach() + ":"
								+ dateFormat.format(item.date()) + ":"
								+ (item.attributedTo() == null ? "null":item.attributedTo().name()) + ":"
								+ (item.category() == null ? "null":item.category().name()));
						bw.newLine();
					}
				}
			}
			bw.flush();
			bw.close();
		}catch(IOException e){e.printStackTrace();}
		
	}	
	public static void saveAccount(BB_Account account)
	{
		File dir = new File("AccountFiles");
		if(!dir.exists())
		{
			dir.mkdir();
		}
		saveAccount(account,new File(defaultDirectory + account.name() + ".txt"));
		
	}
	public static BB_Item createItem(BB_Account account, String iNum, String name, String numItems, String costEach, String date, String attTo,String cat)
	{	
		BB_Item item = new BB_Item();
		item.itemNumber(Integer.parseInt(iNum));
		item.name(name);
		item.numberOfItems(Integer.parseInt(numItems));
		item.costPerEach(Double.parseDouble(costEach));
		try{
			item.date(dateFormat.parse(date));
		}catch(Exception e){item.date(new Date());}
		for(BB_Profile prof : account.profiles())
		{
			if(prof.name().equals(attTo))
			{
				item.attributedTo(prof);
			}
		}
		for(BB_Category category : account.categories())
		{
			if(category.name().equals(cat))
			{
				item.category(category);
			}
		}
		return item;
	}
}
