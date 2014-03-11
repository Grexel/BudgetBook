package BB_Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import BB_Model.*;

import javax.swing.*;

public class BB_WindowApplication 
{
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
	private JPanel monthlyLogPanel, editorPanel;
	
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
		monthlyLogPanel = new JPanel();
		editorPanel = new JPanel();
		tabbedPanels.addTab("Overview",overviewPanel);
		tabbedPanels.addTab("Monthly Log",monthlyLogPanel);
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
		private JTextField costTF;
		private JComboBox paymentToCB;
		private JComboBox categoriesCB;
		private JButton confirmPaymentBT;
		
		public BB_AddPaymentPanel(BB_WindowApplication window)
		{
			this.window = window;
			textLB = new JLabel("Add Payment: ");
			costLB = new JLabel("Enter Cost: ");
			costTF = new JTextField(10);
			paymentToCB = new JComboBox();
			categoriesCB = new JComboBox();
			confirmPaymentBT = new JButton("Enter Payment");
			confirmPaymentBT.addActionListener(this);
			
			this.add(textLB);
			this.add(paymentToCB);
			this.add(costLB);
			this.add(costTF);
			this.add(categoriesCB);
			this.add(confirmPaymentBT);
		}
		public void resetPanel()
		{
			costTF.setText("");
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
						window.account().addDebtPayment(debt.name());
					}
				}
				for(BB_Utility util : window.account().utilities())
				{
					if(paymentName.equals(util.name()))
					{
						window.account().addUtilityPayment(util.name(), cost);
					}
				}
				for(BB_Disposable disp : window.account().spendingTabs())
				{
					if(paymentName.equals(disp.name()))
					{
						ArrayList<BB_Item> items = new ArrayList<BB_Item>();
						items.add(window.account().generatePayment("ItemName", 1, cost, null, category));
						window.account().addDisposablePayment(disp.name(),
								window.account().generateReceipt(items, "Receipt Name" , 0));
					}
				}
				
			}
			
		}
	}
	public class BB_OverviewPanel extends JPanel
	{
		private BB_WindowApplication window;

		private JLabel welcomeLB, balanceLB;
		private JTextArea accountOverviewTA;
		private JScrollPane scrollPane;
		
		public BB_OverviewPanel(BB_WindowApplication window)
		{
			this.window = window;
			this.setLayout(new BorderLayout());
			welcomeLB = new JLabel("Welcome: ");
			balanceLB = new JLabel("Balance: ");
			accountOverviewTA = new JTextArea(20, 40);
			scrollPane = new JScrollPane(accountOverviewTA); 
			accountOverviewTA.setEditable(false);
			this.add(welcomeLB,BorderLayout.NORTH);
			this.add(balanceLB,BorderLayout.CENTER);
			this.add(scrollPane,BorderLayout.SOUTH);
		}
		public void resetPanel()
		{
			welcomeLB.setText("Welcome: " + window.account().name());
			balanceLB.setText("Balance: " + window.account().balance());
			accountOverviewTA.setText("");
			for(BB_Debt debt : window.account().debts())
			{
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
