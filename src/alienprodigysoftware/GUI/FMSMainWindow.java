package alienprodigysoftware.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import alienprodigysoftware.Configuration.AppConfig;
import alienprodigysoftware.Configuration.AssemblyVersion;
import alienprodigysoftware.Configuration.BackupProfile;
import alienprodigysoftware.backupsystem.BackupProcessor;
import javax.swing.JScrollPane;

public class FMSMainWindow
{

	private JFrame frame;
	private BackupProfileJDialog backupProfileJDialog;
	private AppConfig appConfig;
	private static DefaultTableModel profileModel;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					FMSMainWindow window = new FMSMainWindow();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FMSMainWindow()
	{
		initialize();
		LoadConfigurationFile();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		backupProfileJDialog = new BackupProfileJDialog();
		backupProfileJDialog.addComponentListener(new ComponentListener()
		{
			
			@Override
			public void componentShown(ComponentEvent arg0)
			{
				// TODO Auto-generated method stub
				System.out.print("test");
			}
			
			@Override
			public void componentResized(ComponentEvent arg0)
			{
				// TODO Auto-generated method stub
				System.out.print("test");
			}
			
			@Override
			public void componentMoved(ComponentEvent arg0)
			{
				// TODO Auto-generated method stub
				System.out.print("test");
			}
			
			@Override
			public void componentHidden(ComponentEvent arg0)
			{
				// TODO Auto-generated method stub
				System.out.print("test");
				LoadConfigurationFile();
				
			}
		});
			
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 321);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton BackupButton = new JButton("Backup");
		BackupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PerformBackup();
			}
		});
		BackupButton.setBounds(319, 233, 117, 25);
		frame.getContentPane().add(BackupButton);
		
		JLabel lblBackupProfiles = new JLabel("Backup profiles:");
		lblBackupProfiles.setBounds(12, 12, 141, 15);
		frame.getContentPane().add(lblBackupProfiles);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				backupProfileJDialog.LoadBackupProfile(0);
			}
		});
		btnAdd.setBounds(12, 186, 82, 25);
		frame.getContentPane().add(btnAdd);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(218, 186, 100, 25);
		frame.getContentPane().add(btnRemove);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 50, 3, 3);
		frame.getContentPane().add(scrollPane);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 28, 424, 146);
		frame.getContentPane().add(scrollPane_1);
		
		table = new JTable();
		scrollPane_1.setViewportView(table);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				backupProfileJDialog.LoadBackupProfile(table.getSelectedColumn());
			}
		});
		btnEdit.setBounds(106, 186, 100, 25);
		frame.getContentPane().add(btnEdit);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnPreferences = new JMenu("Preferences");
		menuBar.add(mnPreferences);
		
		JMenu mnTestItem = new JMenu("test item");
		mnPreferences.add(mnTestItem);
		
		JMenu mnTest = new JMenu("Help");
		menuBar.add(mnTest);
		
		JMenuItem mntmGoto = new JMenuItem("About");
		mntmGoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Current version: " + AssemblyVersion.AssemblyVersion
													+"\nDeveloper: Grant Woodford"); 
			}
		});
		mnTest.add(mntmGoto);
	}
	
	private void LoadConfigurationFile()
	{
		List<BackupProfile> profiles = this.appConfig.getBackupProfiles();
			
		profileModel = new DefaultTableModel(
				new String[] 
				{
					"Type", 
					"Username",	
					"Bucket", 
					"Used", 
					"Space" 
				}
			, 0);
		
		// add rows
		for (int i = 0; i < profiles.size(); i++)
		{
			String [] profile = {"Amazon S3", profiles.get(i).get_Username(), profiles.get(i).get_Bucket(), "0", "0"}; 
		    profileModel.addRow(profile);
		}
		
		table.setModel(profileModel);
		table.repaint();
	}
	
	private static void PerformBackup()
	{
		// start thread
		Runnable r = new Runnable() 
		{
		  public void run() 
		  {
			  BackupProcessor bp = new BackupProcessor();
			  bp.ProcessAmazonS3Backup();
		  }
		};
		
		Thread thr = new Thread(r);
		thr.start();
	}
}
