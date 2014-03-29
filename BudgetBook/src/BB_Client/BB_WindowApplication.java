package BB_Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import BB_Model.*;

import javax.swing.*;

public class BB_WindowApplication 
{
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private BB_Account account;
	
	private JFrame winFrame;
	private JPanel winPanel;
	
	//MenuBar
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private BB_ExitMI exitMI;	private BB_NewBudgetMI newBudgetMI;
	private BB_SaveBudgetMI saveBudgetMI;  private BB_LoadBudgetMI loadBudgetMI;
	
	//Tabbed Panels
	private JTabbedPane tabbedPanels;
	private BB_AddPaymentPanel addPaymentPanel;
	private BB_OverviewPanel overviewPanel;
	private BB_MonthlyLogPanel monthlyLogPanel;
	private BB_ItemListPanel itemListPanel;
	private BB_SearchPanel searchPanel;
	private BB_EditorPanel editorPanel;
	
	public BB_WindowApplication()
	{
		winFrame = new JFrame("Budget Book");
		winPanel = new JPanel(new BorderLayout());
		winFrame.add(winPanel);
		initializeMenuBar();
		initializeAddPaymentPanel();
		initializeTabbedPanels();
		winFrame.setJMenuBar(menuBar);
		winFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//winFrame.pack();
		winFrame.setSize(800,600);
		winFrame.setVisible(true);
	}


	private void initializeMenuBar()
	{
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		newBudgetMI = new BB_NewBudgetMI(this);
		saveBudgetMI = new BB_SaveBudgetMI(this);
		loadBudgetMI = new BB_LoadBudgetMI(this);
		exitMI = new BB_ExitMI();
		fileMenu.add(newBudgetMI);
		fileMenu.add(saveBudgetMI);
		fileMenu.add(loadBudgetMI);
		fileMenu.add(exitMI);
		menuBar.add(fileMenu);
	}
	private void initializeTabbedPanels() 
	{
		tabbedPanels = new JTabbedPane();
		overviewPanel = new BB_OverviewPanel(this);
		monthlyLogPanel = new BB_MonthlyLogPanel(this);
		itemListPanel = new BB_ItemListPanel(this);
		searchPanel = new BB_SearchPanel(this);
		editorPanel = new BB_EditorPanel(this);
		tabbedPanels.addTab("Overview",overviewPanel);
		tabbedPanels.addTab("Monthly Log",monthlyLogPanel);
		tabbedPanels.addTab("Item List",itemListPanel);
		tabbedPanels.addTab("Search",searchPanel);
		tabbedPanels.addTab("Editor",editorPanel);
		winPanel.add(tabbedPanels,BorderLayout.CENTER);
	}
	private void initializeAddPaymentPanel()
	{
		addPaymentPanel = new BB_AddPaymentPanel(this);
		winPanel.add(addPaymentPanel,BorderLayout.NORTH);
	}

	//
	// Functions
	//
	private void resetPanels() {
		addPaymentPanel.resetPanel();
		overviewPanel.resetPanel();
		monthlyLogPanel.resetPanel();
		itemListPanel.resetPanel();
		searchPanel.resetPanel();
	}
	
	
	//
	//   GETTER SETTERS
	//
	public void account(BB_Account account)
	{
		this.account = account;
		winFrame.setTitle("Budget Book: " + account.name());
		resetPanels();
	}
	public BB_Account account(){
		return account;
	}
	public JFrame winFrame(){

		return winFrame;
	}
	
	
	//
	//   ADDPAYMENTPANEL
	//
	public class BB_AddPaymentPanel extends JPanel implements ActionListener
	{
		private BB_WindowApplication window;

		private JLabel textLB;
		private JLabel costLB;
		private JLabel descriptionLB;
		private JTextField costTF;
		private JTextField descriptionTF;
		private JComboBox paymentToCB;
		private JComboBox categoriesCB;
		private JButton confirmPaymentBT;
		
		public BB_AddPaymentPanel(BB_WindowApplication window)
		{
			super();
			this.window = window;
			textLB = new JLabel("Add Payment: ");
			costLB = new JLabel("Enter Cost: ");
			descriptionLB = new JLabel("Description:");
			costTF = new JTextField(10);
			descriptionTF = new JTextField(10);
			paymentToCB = new JComboBox();
			categoriesCB = new JComboBox();
			confirmPaymentBT = new JButton("Enter Payment");
			confirmPaymentBT.addActionListener(this);
			
			this.add(textLB);
			this.add(paymentToCB);
			this.add(costLB);
			this.add(costTF);
			this.add(categoriesCB);
			this.add(descriptionLB);
			this.add(descriptionTF);
			this.add(confirmPaymentBT);
		}
		public void resetPanel()
		{
			costTF.setText("");
			descriptionTF.setText("");
			paymentToCB.removeAllItems();
			categoriesCB.removeAllItems();
			
			for(BB_Debt debt : window.account().debts())
			{
				paymentToCB.addItem(debt.name());
			}
			for(BB_Utility util : window.account().utilities())
			{
				paymentToCB.addItem(util.name());
			}
			for(BB_Earning inc : window.account().income())
			{
				paymentToCB.addItem(inc.name());
			}
			for(BB_Disposable disp : window.account().spendingTabs())
			{
				paymentToCB.addItem(disp.name());
			}
			for(BB_Category cat : window.account().categories())
			{
				categoriesCB.addItem(cat.name());
			}
			
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == confirmPaymentBT)
			{
				String paymentName = (String)paymentToCB.getSelectedItem();
				String catName = (String)categoriesCB.getSelectedItem();
				double cost = Double.parseDouble(costTF.getText());
				BB_Category category = null;

				for(BB_Category cat : window.account().categories())
				{
					if(catName.equals(cat.name()))
					{
						category = cat;
					}
				}
				for(BB_Debt debt : window.account().debts())
				{
					if(paymentName.equals(debt.name()))
					{
						if(cost == 0)
							window.account().addDebtPayment(debt.name());
						else
							window.account().addCustomDebtPayment(debt.name(), cost);
					}
				}
				for(BB_Utility util : window.account().utilities())
				{
					if(paymentName.equals(util.name()))
					{
						window.account().addUtilityPayment(util.name(), cost);
					}
				}
				for(BB_Earning incName : window.account().income())
				{
					if(paymentName.equals(incName.name()))
					{
						window.account().addIncomePayment(incName.name(), cost);
					}
				}
				for(BB_Disposable disp : window.account().spendingTabs())
				{
					if(paymentName.equals(disp.name()))
					{
						ArrayList<BB_Item> items = new ArrayList<BB_Item>();
						items.add(window.account().generatePayment("ItemName", 1, cost, null, category));
						window.account().addDisposablePayment(disp.name(),
								window.account().generateReceipt(items, descriptionTF.getText() , 0));
					}
				}
				
			}
			window.resetPanels();
		}
	}
	

	public class BB_OverviewPanel extends JPanel
	{
		private BB_WindowApplication window;
		private JPanel dataPanel;

		private JLabel welcomeLB, balanceLB,monthlyPaymentLB,monthlyEarningsLB, monthlyBalanceLB;
		private JTextArea accountOverviewTA;
		private JScrollPane scrollPane;
		
		public BB_OverviewPanel(BB_WindowApplication window)
		{
			this.window = window;
			this.setLayout(new BorderLayout());
			dataPanel = new JPanel();
			dataPanel.setLayout(new BorderLayout());
			welcomeLB = new JLabel("Welcome: ");
			balanceLB = new JLabel("Account Balance: ");
			monthlyPaymentLB = new JLabel("Monthly Payments: ");
			monthlyEarningsLB = new JLabel("Monthly Earnings: ");
			monthlyBalanceLB = new JLabel("Monthly Balance: ");
			
			accountOverviewTA = new JTextArea(20, 40);
			scrollPane = new JScrollPane(accountOverviewTA); 
			accountOverviewTA.setEditable(false);
			
			dataPanel.add(monthlyPaymentLB,BorderLayout.NORTH);
			dataPanel.add(monthlyEarningsLB,BorderLayout.CENTER);
			dataPanel.add(monthlyBalanceLB,BorderLayout.SOUTH);
			
			this.add(welcomeLB,BorderLayout.NORTH);
			this.add(balanceLB,BorderLayout.WEST);
			this.add(dataPanel,BorderLayout.CENTER);
			this.add(scrollPane,BorderLayout.SOUTH);
		}
		public void resetPanel()
		{
			welcomeLB.setText("Welcome: " + window.account().name());
			balanceLB.setText("Balance: " + window.account().balance());
			monthlyPaymentLB.setText("Monthly Payments: " + window.account().monthlyPayments(new Date()));
			monthlyEarningsLB.setText("Monthly Earnings: " + window.account().monthlyEarnings(new Date()));
			monthlyBalanceLB.setText("Monthly Balance: " + (
					window.account().monthlyEarnings(new Date()) 
					- window.account().monthlyPayments(new Date())));
			
			accountOverviewTA.setText("");
			for(BB_Debt debt : window.account().debts())
			{
				if(!debt.isClosed())
				accountOverviewTA.append("Debt: " + debt.name() + "  Balance: " + debt.currentBalance() + "\n");
			}
			for(BB_Utility util : window.account().utilities())
			{
				accountOverviewTA.append("Util: " + util.name() +"   " + (util.isCurrent() ? "Current" : "DUE") + "\n");
			}
			for(BB_Disposable disp : window.account().spendingTabs())
			{
				double spentThisMonth = 0;
				for(BB_Receipt rec : disp.getReceipts(new Date()))
				{
					spentThisMonth += rec.total();
				}
				accountOverviewTA.append("Disposable: " + disp.name() + " Spent: " + spentThisMonth + "\n");
			}
			for(BB_Earning inc : window.account().income())
			{
				double earnThisMonth = 0;
				for(BB_Item rec : inc.getEarnings(new Date()))
				{
					earnThisMonth += rec.costPerEach();
				}
				accountOverviewTA.append("Income: " + inc.name() + " Earned: " + earnThisMonth + "\n");
			}
			
		}
	}
	public class BB_EditorPanel extends JPanel implements ActionListener
	{
		private BB_WindowApplication window;
		
		private JButton addDebtBT, addUtilityBT, addSpendingTabBT, addEarningBT,
			addProfileBT, addCategoryBT;
		private JButton editDebtBT, editUtilityBT, editSpendingTabBT, editEarningBT,
			editProfileBT, editCategoryBT;
		public BB_EditorPanel(BB_WindowApplication window)
		{
			super();
			this.window = window;
			addDebtBT = new JButton("Add Debt");
			addDebtBT.addActionListener(this);
			addUtilityBT = new JButton("Add Utility");
			addUtilityBT.addActionListener(this);
			addSpendingTabBT = new JButton("Add Disposable");
			addSpendingTabBT.addActionListener(this);
			addEarningBT = new JButton("Add Earning");
			addEarningBT.addActionListener(this);
			addProfileBT = new JButton("Add Profile");
			addProfileBT.addActionListener(this);
			addCategoryBT = new JButton("Add Category");
			addCategoryBT.addActionListener(this);

			editDebtBT = new JButton("Edit Debt");
			editDebtBT.addActionListener(this);
			editUtilityBT = new JButton("Edit Utility");
			editUtilityBT.addActionListener(this);
			editSpendingTabBT = new JButton("Edit Disposable");
			editSpendingTabBT.addActionListener(this);
			editEarningBT = new JButton("Edit Earning");
			editEarningBT.addActionListener(this);
			editProfileBT = new JButton("Edit Profile");
			editProfileBT.addActionListener(this);
			editCategoryBT = new JButton("Edit Category");
			editCategoryBT.addActionListener(this);
			
			this.add(addDebtBT);
			this.add(addUtilityBT);
			this.add(addSpendingTabBT);
			this.add(addEarningBT);
			this.add(addProfileBT);
			this.add(addCategoryBT);

			this.add(editDebtBT);
			this.add(editUtilityBT);
			this.add(editSpendingTabBT);
			this.add(editEarningBT);
			this.add(editProfileBT);
			this.add(editCategoryBT);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == addDebtBT)
			{
				String name;
				String payment;
				name = (String)JOptionPane.showInputDialog(
						window.winFrame(),
	                    "Please enter a name for your new debt.",
	                    "New Debt",
	                    JOptionPane.PLAIN_MESSAGE,
	                    null,
	                    null,
	                    "");
				if(name != null) 
				{
					payment = (String)JOptionPane.showInputDialog(
							window.winFrame(),
		                    "Please enter the payment for " + name,
		                    name + "Payment",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    null,
		                    "");
					BB_Debt d = new BB_Debt(name);
					d.payment(Double.parseDouble(payment));
					window.account().addDebt(d);
				}
			}
			
			if(e.getSource() == addUtilityBT)
			{
				String s = (String)JOptionPane.showInputDialog(
						window.winFrame(),
	                    "Please enter a name for your new utility.",
	                    "New Utility",
	                    JOptionPane.PLAIN_MESSAGE,
	                    null,
	                    null,
	                    "");
				if(s != null) window.account().addUtility(new BB_Utility(s));
			}
			if(e.getSource() == addSpendingTabBT)
			{
				String s = (String)JOptionPane.showInputDialog(
						window.winFrame(),
	                    "Please enter a name for your new spending tab.",
	                    "New Disposable",
	                    JOptionPane.PLAIN_MESSAGE,
	                    null,
	                    null,
	                    "");
				if(s != null) window.account().addDisposable(new BB_Disposable(s));
			}
			if(e.getSource() == addEarningBT)
			{
				String s = (String)JOptionPane.showInputDialog(
						window.winFrame(),
	                    "Please enter a name for your new Income.",
	                    "New Income",
	                    JOptionPane.PLAIN_MESSAGE,
	                    null,
	                    null,
	                    "");
				if(s != null) window.account().addEarning(new BB_Earning(s));
			}
			if(e.getSource() == addProfileBT)
			{
				String s = (String)JOptionPane.showInputDialog(
						window.winFrame(),
	                    "Please enter a name for your new Profile.",
	                    "New Profile",
	                    JOptionPane.PLAIN_MESSAGE,
	                    null,
	                    null,
	                    "");
				if(s != null) window.account().addProfile(new BB_Profile(s));
			}
			if(e.getSource() == addCategoryBT)
			{
				String s = (String)JOptionPane.showInputDialog(
						window.winFrame(),
	                    "Please enter a name for your new Category.",
	                    "New Category",
	                    JOptionPane.PLAIN_MESSAGE,
	                    null,
	                    null,
	                    "");
				if(s != null) window.account().addCategory(new BB_Category(s));
			}
			if(e.getSource() == editDebtBT)
			{
				new BB_EditDebtFrame(window);
			}
			window.resetPanels();
		}
		
	}
	public class BB_MonthlyLogPanel extends JPanel implements ActionListener
	{
		private BB_WindowApplication window;
		
		private JComboBox monthCB, yearCB;
		private JTextArea logResultsTA;
		private JScrollPane scrollPane;
		private JPanel cBPanel;
		
		public BB_MonthlyLogPanel(BB_WindowApplication window)
		{
			super();
			this.window = window;
			monthCB = new JComboBox();
			monthCB.addActionListener(this);
			yearCB = new JComboBox();
			yearCB.addActionListener(this);
			logResultsTA = new JTextArea(20,20);
			scrollPane = new JScrollPane(logResultsTA); 
			logResultsTA.setEditable(false);
			cBPanel = new JPanel();

			cBPanel.add(monthCB);
			cBPanel.add(yearCB);
			this.setLayout(new BorderLayout());
			this.add(cBPanel, BorderLayout.NORTH);
			this.add(scrollPane, BorderLayout.CENTER);
		}
		public void resetPanel()
		{
			monthCB.removeAllItems();
			yearCB.removeAllItems();
			logResultsTA.setText("");
			monthCB.addItem("No Month");
			monthCB.addItem("January");
			monthCB.addItem("February");
			monthCB.addItem("March");
			monthCB.addItem("April");
			monthCB.addItem("May");
			monthCB.addItem("June");
			monthCB.addItem("July");
			monthCB.addItem("August");
			monthCB.addItem("September");
			monthCB.addItem("October");
			monthCB.addItem("November");
			monthCB.addItem("December");

			Calendar calendar = new GregorianCalendar();
			calendar.setTime(window.account().dateCreated());
			int yearStart = calendar.get(Calendar.YEAR);
			calendar.setTime(new Date());
			int yearEnd = calendar.get(Calendar.YEAR);
			for(;yearStart <= yearEnd;yearStart++)
			{
				yearCB.addItem(yearStart);
			}
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {

			logResultsTA.setText("");
			
			double monthlyPayments = 0;
			double monthlyEarnings = 0;
			double debtPayments = 0;
			double utilityPayments = 0;
			double disposablePayments = 0;
			Date dateUsed = getDateChosen();
			
			
			logResultsTA.append("Debts:" + "\n");
			for(BB_Debt debt : account.debts())
			{
				debtPayments += debt.payment();	
				if(debt.isCurrent(dateUsed))
				{
					for(BB_Item payment : debt.getPayments(dateUsed))
					{
						logResultsTA.append("$ " + debt.name() + " " + payment.costPerEach() + "\n");
						monthlyPayments += payment.costPerEach();
					}
				}
				else
				{
					if(!debt.isClosed())
					logResultsTA.append("  " + debt.name() + " " + debt.payment() + "\n");
				}
			}
			logResultsTA.append("Utilities:" + "\n");
			for(BB_Utility utility : account.utilities())
			{
				logResultsTA.append("  " + utility.name() + "  avg: " + utility.averagePayment() + "\n");
				for(BB_Item payment : utility.getPayment(dateUsed))
				{
					logResultsTA.append("    " + payment.name() + " " + payment.costPerEach() + "\n");
					monthlyPayments += payment.costPerEach();
					utilityPayments += payment.costPerEach();
				}
			}
			logResultsTA.append("Income:" + "\n");
			for(BB_Earning income : account.income())
			{
				double totalIncome = 0;
				for(BB_Item payment : income.getEarnings(dateUsed))
				{
					totalIncome += payment.costPerEach();
				}
				logResultsTA.append("  " + income.name() + ": " + totalIncome + "\n");
				for(BB_Item payment : income.getEarnings(dateUsed))
				{
					logResultsTA.append("    " + payment.name() + " " + payment.costPerEach() + "\n");
					monthlyEarnings += payment.costPerEach();
				}
			}
			logResultsTA.append("Disposable Income:" + "\n");
			for(BB_Disposable dispose : account.spendingTabs())
			{
				double totalDisp = 0;
				for(BB_Receipt receipt : dispose.getReceipts(dateUsed))
				{
					totalDisp += receipt.total();
				}
				logResultsTA.append("  " + dispose.name() + ": " + totalDisp + "\n");
				for(BB_Receipt receipt : dispose.getReceipts(dateUsed))
				{
					logResultsTA.append("   $" + receipt.name() + " " + receipt.total() + "\n");
					monthlyPayments += receipt.total();
					disposablePayments += receipt.total();
				}
			}
			logResultsTA.append("Total Debts = " + debtPayments + "\n");
			logResultsTA.append("Utility Payments = " + utilityPayments + "\n");
			logResultsTA.append("Disposable Payments  = " + disposablePayments + "\n");
			logResultsTA.append("" + "\n");
			logResultsTA.append("Monthly Payments = " + monthlyPayments + "\n");
			logResultsTA.append("Monthly Earnings = " + monthlyEarnings + "\n");
			logResultsTA.append("Monthly Balance  = " + (monthlyEarnings - monthlyPayments) + "\n");
		}
		public Date getDateChosen()
		{
			String date = "";
			date += monthCB.getSelectedIndex() + "/" + yearCB.getSelectedItem();
			SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");
			try{
			return format.parse(date);
			}catch(Exception e){}
			return new Date();
		}
	}
	public class BB_SearchPanel extends JPanel implements ActionListener
	{
		private BB_WindowApplication window;
		
		private JList<String> paymentGroupsLT, categoryLT;
		private JScrollPane paymentSP, categorySP;
		private JTextField startDateTF, endDateTF;
		private JTextArea outputTA;
		private JScrollPane outputSP;
		private JButton confirmBT;
		
		public BB_SearchPanel(BB_WindowApplication window)
		{
			super();
			this.window = window;
			paymentGroupsLT = new JList();
			categoryLT = new JList();
			paymentSP = new JScrollPane(paymentGroupsLT);
			paymentSP.setPreferredSize(new Dimension(250, 80));
			categorySP = new JScrollPane(categoryLT);
			categorySP.setPreferredSize(new Dimension(250, 80));
			startDateTF = new JTextField(10);
			endDateTF = new JTextField(10); 
			outputTA = new JTextArea(20,20);
			outputSP = new JScrollPane(outputTA); 
			outputSP.setPreferredSize(new Dimension(600, 400));
			confirmBT = new JButton("Search");
			confirmBT.addActionListener(this);

			this.add(paymentSP);
			this.add(categorySP);
			this.add(startDateTF);
			this.add(endDateTF);
			this.add(confirmBT);
			this.add(outputSP);
		}
		
		public void resetPanel()
		{
			DefaultListModel<String> paymentModel = new DefaultListModel<String>();
			DefaultListModel<String> categoryModel = new DefaultListModel<String>();
			for(BB_Debt debt : window.account().debts())
			{
				paymentModel.addElement(debt.name());
			}
			for(BB_Utility util : window.account().utilities())
			{
				paymentModel.addElement(util.name());
			}
			for(BB_Earning inc : window.account().income())
			{
				paymentModel.addElement(inc.name());
			}
			for(BB_Disposable disp : window.account().spendingTabs())
			{
				paymentModel.addElement(disp.name());
			}
			for(BB_Category cat : window.account().categories())
			{
				categoryModel.addElement(cat.name());
			}
			paymentGroupsLT.setModel(paymentModel);
			categoryLT.setModel(categoryModel);
			outputTA.setText("");
			startDateTF.setText("Start");
			endDateTF.setText("End");
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			if(e.getSource() == confirmBT)
			{
				outputTA.setText("");
				String summaryText = "";
				//Made Global variable
				//SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				Date startDate = new Date(), endDate = new Date();
				try
				{
				 startDate = dateFormat.parse(startDateTF.getText());
				 endDate = dateFormat.parse(endDateTF.getText());
				}catch(Exception exc){System.out.println("Date Error");}

				for(String catName : categoryLT.getSelectedValuesList())
				{
					summaryText += "Category - " + catName + "\n";
					double categoryTotal = 0;
					outputTA.append("Category - " + catName + "\n");
					for(String payName : paymentGroupsLT.getSelectedValuesList())
					{
						for(BB_Debt debt : window.account().debts())
						{
							if(payName.equals(debt.name()))
							{
								double debtTotal = 0;
								for(BB_Item item : debt.getPayments(startDate, endDate))
								{
									if(catName.equals(item.category().name()))
									{
										outputTA.append("  Debt - " + debt.name() + "\n");
										categoryTotal += item.costPerEach() * item.numberOfItems();
										debtTotal += item.costPerEach() * item.numberOfItems();
										outputTA.append("    Date " + dateFormat.format(item.date())
												+ " Cost - " + item.costPerEach() + "\n");
									}
								}
								if(debtTotal != 0)
									summaryText += "  Debt - " + debt.name() + " Total: " + debtTotal + "\n";
							}
						}
						for(BB_Utility util : window.account().utilities())
						{
							if(payName.equals(util.name()))
							{
								double utilTotal = 0;
								for(BB_Item item : util.getPayments(startDate, endDate))
								{
									if(catName.equals(item.category().name()))
									{
										outputTA.append("  Utility - " + util.name() + "\n");
										categoryTotal += item.costPerEach() * item.numberOfItems();
										utilTotal += item.costPerEach() * item.numberOfItems();
										outputTA.append("    Date " + dateFormat.format(item.date())
												+ " Cost - " + item.costPerEach() + "\n");
									}
								}
								if(utilTotal != 0)
								summaryText += "  Util - " + util.name() + " Total: " + utilTotal + "\n";
							}
						}
						for(BB_Earning inc : window.account().income())
						{
							if(payName.equals(inc.name()))
							{
								double incomeTotal = 0;
								for(BB_Item item : inc.getEarnings(startDate, endDate))
								{
									if(catName.equals(item.category().name()))
									{
										outputTA.append("  Income - " + inc.name() + "\n");
										categoryTotal += item.costPerEach() * item.numberOfItems();
										incomeTotal += item.costPerEach() * item.numberOfItems();
										outputTA.append("    Date " + dateFormat.format(item.date())
												+ " Earned - " + item.costPerEach() + "\n");
									}
								}
								if(incomeTotal != 0)
								summaryText += "  Income - " + inc.name() + " Total: " + incomeTotal + "\n";
							}
						}
						for(BB_Disposable disp : window.account().spendingTabs())
						{
							if(payName.equals(disp.name()))
							{
								double dispTotal = 0;
								for(BB_Receipt rec : disp.getReceipts(startDate, endDate))
								{
									for(BB_Item item : rec.listOfItems())
									{
										if(catName.equals(item.category().name()))
										{
											outputTA.append("  Disposable - " + disp.name() + "\n");
											categoryTotal += item.costPerEach() * item.numberOfItems();
											dispTotal += item.costPerEach() * item.numberOfItems();
											outputTA.append("    Receipt - " + rec.name() + "\n");
											outputTA.append("      Date " + dateFormat.format(item.date())
													+ " Cost - " + (item.costPerEach() * item.numberOfItems()) + "\n");
										}
									}
								}
								if(dispTotal != 0)
								summaryText += "  Disposable - " + disp.name() + " Total: " + dispTotal + "\n";
							}
						}//end for disposable
					}//end payname
					outputTA.append("Category " + catName + " Total: " + categoryTotal + "\n");
					summaryText += "Category " + catName + " Total: " + categoryTotal + "\n";
				}//end catname
				//Generate begin and end dates
				outputTA.append(summaryText);
			}
			
		}
		
	}
	public class BB_ItemListPanel extends JPanel implements ActionListener
	{
		private BB_WindowApplication window;
		private JPanel itemListJP;
		private JScrollPane panelSP;
		public BB_ItemListPanel(BB_WindowApplication window)
		{
			super();
			this.window = window;
			itemListJP = new JPanel();
			panelSP = new JScrollPane(itemListJP);
			itemListJP.setLayout(new BoxLayout(itemListJP,BoxLayout.Y_AXIS));
			this.add(panelSP);
		}
		public void resetPanel()
		{
			itemListJP.removeAll();
			for(BB_Debt debt : window.account().debts())
			{
				for(BB_Item item : debt.listOfPayments())
				{
					itemListJP.add(new BB_ItemPanel(item));
				}
			}
			for(BB_Utility util : window.account().utilities())
			{
				for(BB_Item item : util.listOfPayments())
				{
					itemListJP.add(new BB_ItemPanel(item));
				}
			}
			for(BB_Disposable disp : window.account().spendingTabs())
			{
				for(BB_Receipt rec : disp.listOfPayments())
				{
					for(BB_Item item : rec.listOfItems())
					{
						itemListJP.add(new BB_ItemPanel(item));
					}
					
				}
			}
			for(BB_Earning inc : window.account().income())
			{
				for(BB_Item item : inc.listOfPayments())
				{
					itemListJP.add(new BB_ItemPanel(item));
				}
			}
			panelSP.setPreferredSize(this.getSize());
			
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	public class BB_ItemPanel extends JPanel implements ActionListener
	{
		private BB_Item item;
		private JTextField itemNumberTF, nameTF, numItemTF, costEachTF, dateTF, profileTF, categoryTF;
		public BB_ItemPanel(BB_Item item)
		{
			super();
			this.item = item;
			itemNumberTF = new JTextField(5);
			nameTF = new JTextField(8);
			numItemTF = new JTextField(5);
			costEachTF = new JTextField(5);
			dateTF = new JTextField(10);
			profileTF = new JTextField(8);
			categoryTF = new JTextField(8);
			
			itemNumberTF.setText(Integer.toString(item.itemNumber()));
			nameTF.setText(item.name());
			numItemTF.setText(Integer.toString(item.numberOfItems()));
			costEachTF.setText(Double.toString(item.costPerEach()));
			dateTF.setText(dateFormat.format(item.date()));
			profileTF.setText(item.attributedTo().name());
			categoryTF.setText(item.category().name());
			
			this.add(itemNumberTF);
			this.add(nameTF);
			this.add(numItemTF);
			this.add(costEachTF);
			this.add(dateTF);
			this.add(profileTF);
			this.add(categoryTF);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	//
	//   EDITOR ITEMS
	//
	public class BB_EditDebtFrame extends JFrame implements ActionListener
	{
		private BB_WindowApplication window;
		private JPanel panel;
		private JButton saveChangesBT;
		private ArrayList<BB_DebtEditPanel> debtPanels;
		
		public BB_EditDebtFrame(BB_WindowApplication window)
		{
			super("Edit Debt");
			this.window = window;
			panel = new JPanel();
			initializeLabelPanel();
			initializeDebtPanels();
			
			this.add(panel);
			this.setPreferredSize(new Dimension(600,400));
			this.pack();
			this.setVisible(true);
		}
		public void initializeDebtPanels()
		{
			debtPanels = new ArrayList<BB_DebtEditPanel>();
			saveChangesBT = new JButton("Save Changes");
			saveChangesBT.addActionListener(this);
			panel.add(saveChangesBT);
			for(BB_Debt debt : window.account().debts())
			{
				BB_DebtEditPanel debtPanel = new BB_DebtEditPanel(debt);
				panel.add(debtPanel);
				debtPanels.add(debtPanel);
			}
		}
		
		public void initializeLabelPanel()
		{
			JPanel p = new JPanel();
			p.add(new JTextField("Debt Name" , 15));
			p.add(new JTextField("Initial Bal." , 10));
			p.add(new JTextField("Current Bal." , 10));
			p.add(new JTextField("Payment" , 10));
			p.add(new JTextField("Principal" , 10));
			p.add(new JTextField("Interest" , 10));
			p.add(new JTextField("Paid Off" , 6));
			panel.add(p);
			
		}
		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == saveChangesBT)
			{
				for(BB_DebtEditPanel debtPanel : debtPanels)
				{
					debtPanel.saveChanges();
				}
			}
			
		}
	}
	public class BB_DebtEditPanel extends JPanel implements ItemListener
	{
		private BB_Debt debt;
		private JTextField debtNameTF, initialBalanceTF, currentBalanceTF;
		private JTextField paymentTF, principalTF, interestTF;
		private JCheckBox isClosedRB;
		
		public BB_DebtEditPanel(BB_Debt debt)
		{
			this.debt = debt;
			debtNameTF = new JTextField(15);
			initialBalanceTF = new JTextField(10);
			currentBalanceTF = new JTextField(10);
			paymentTF = new JTextField(10);
			principalTF = new JTextField(10);
			interestTF = new JTextField(10);
			isClosedRB = new JCheckBox("isClosed");
			//Only change if DebtEditFrame calls savechanges
			//isClosedRB.addItemListener(this);
			
			debtNameTF.setText(debt.name());
			initialBalanceTF.setText(Double.toString(debt.initialBalance()));
			currentBalanceTF.setText(Double.toString(debt.currentBalance()));
			paymentTF.setText(Double.toString(debt.payment()));
			principalTF.setText(Double.toString(debt.principalPayment()));
			interestTF.setText(Double.toString(debt.interestPayment()));
			isClosedRB.setSelected(debt.isClosed());
			
			this.add(debtNameTF);
			this.add(initialBalanceTF);
			this.add(currentBalanceTF);
			this.add(paymentTF);
			this.add(principalTF);
			this.add(interestTF);
			this.add(isClosedRB);
		}
		
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(e.getItemSelectable() == isClosedRB)
			{
				if(e.getStateChange() == ItemEvent.DESELECTED)
				{
					debt.isClosed(false);
				}
				else if(e.getStateChange() == ItemEvent.SELECTED)
				{
					debt.isClosed(true);
				}
			}
		}
		public void saveChanges()
		{
			debt.name(debtNameTF.getText());
			debt.initialBalance(Double.parseDouble(initialBalanceTF.getText()));
			debt.currentBalance(Double.parseDouble(currentBalanceTF.getText()));
			debt.payment(Double.parseDouble(paymentTF.getText()));
			debt.principalPayment(Double.parseDouble(principalTF.getText()));
			debt.interestPayment(Double.parseDouble(interestTF.getText()));
			debt.isClosed(isClosedRB.isSelected());
		}
	}
	
	//
	//   MENU ITEMS
	//
	public class BB_ExitMI extends JMenuItem implements ActionListener
	{
		public BB_ExitMI()
		{
			super("Exit");
			this.addActionListener(this);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);
			
		}
		
	}
	public class BB_NewBudgetMI extends JMenuItem implements ActionListener
	{
		private BB_WindowApplication window;
		public BB_NewBudgetMI(BB_WindowApplication window)
		{
			super("New Budget");
			this.window = window;
			this.addActionListener(this);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			String s = (String)JOptionPane.showInputDialog(
					window.winFrame(),
                    "Please Enter a Name for your new budget book.",
                    "New Budget",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "");
			
			//Add name validation
			if(s != null) window.account(new BB_Account(s));
			window.account().addCategory(new BB_Category("Default"));
			window.account().addProfile(new BB_Profile("Default"));
		}
		
	}
	public class BB_SaveBudgetMI extends JMenuItem implements ActionListener
	{
		private BB_WindowApplication window;
		public BB_SaveBudgetMI(BB_WindowApplication window)
		{
			super("Save Budget");
			this.window = window;
			this.addActionListener(this);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = fc.showOpenDialog(window.winFrame());
	        if (returnVal == JFileChooser.APPROVE_OPTION) 
	        {
	            File file = fc.getSelectedFile();
	            File fileSave = new File(file,window.account().name() + ".txt");
	            BB_FileStorage.saveAccount(window.account(), fileSave);
	        }
		}
		
	}
	public class BB_LoadBudgetMI extends JMenuItem implements ActionListener
	{
		private BB_WindowApplication window;
		
		public BB_LoadBudgetMI(BB_WindowApplication window)
		{
			super("Load Budget");
			this.window = window;
			this.addActionListener(this);
			
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(window.winFrame());
	        if (returnVal == JFileChooser.APPROVE_OPTION) 
	        {
	            File file = fc.getSelectedFile();
	            window.account(BB_FileStorage.loadAccount(file));
	        }
			
		}
		
	}
	public static void main(String[] args)
	{
		new BB_WindowApplication();
	}
}
