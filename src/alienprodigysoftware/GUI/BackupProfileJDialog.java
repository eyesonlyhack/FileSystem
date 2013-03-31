package alienprodigysoftware.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.Console;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JEditorPane;

import alienprodigysoftware.Configuration.AppConfig;
import alienprodigysoftware.Configuration.BackupProfile;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BackupProfileJDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JComboBox accountTypeComboBox;
	private JTextField userNameTextField;
	private JTextField passwordtextField;
	private JTextField bucketNameTextField;
	private JEditorPane filePathsEditorPane;
	private static int BackupProfileIndex;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		try
		{
			BackupProfileJDialog dialog = new BackupProfileJDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);	
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public BackupProfileJDialog()
	{
		setBounds(100, 100, 450, 353);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			accountTypeComboBox = new JComboBox(new String[] {"Amazon S3", "Local"});
			accountTypeComboBox.setBounds(114, 12, 178, 24);
			contentPanel.add(accountTypeComboBox);
		}
		{
			JLabel lblAccount = new JLabel("Account:");
			lblAccount.setBounds(12, 17, 70, 15);
			contentPanel.add(lblAccount);
		}
		{
			JLabel lblUsername = new JLabel("Username:");
			lblUsername.setBounds(12, 44, 104, 15);
			contentPanel.add(lblUsername);
		}
		
		userNameTextField = new JTextField();
		userNameTextField.setBounds(114, 42, 178, 24);
		contentPanel.add(userNameTextField);
		userNameTextField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(12, 74, 104, 15);
		contentPanel.add(lblPassword);
		
		passwordtextField = new JTextField();
		passwordtextField.setColumns(10);
		passwordtextField.setBounds(114, 70, 178, 24);
		contentPanel.add(passwordtextField);
		
		JLabel lblBucketname = new JLabel("Bucketname:");
		lblBucketname.setBounds(12, 101, 104, 15);
		contentPanel.add(lblBucketname);
		
		bucketNameTextField = new JTextField();
		bucketNameTextField.setColumns(10);
		bucketNameTextField.setBounds(114, 98, 178, 24);
		contentPanel.add(bucketNameTextField);
		
		filePathsEditorPane = new JEditorPane();
		filePathsEditorPane.setBounds(12, 148, 424, 128);
		contentPanel.add(filePathsEditorPane);
		
		JLabel lblFilepaths = new JLabel("Filepaths:");
		lblFilepaths.setBounds(12, 131, 104, 15);
		contentPanel.add(lblFilepaths);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton saveButton = new JButton("Save");
				saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						BackupProfile profile = new BackupProfile();
						profile.set_BackupProfileIndex(BackupProfileIndex);
						profile.set_Username(userNameTextField.getText());
						profile.set_Password(passwordtextField.getText());
						profile.set_Bucket(bucketNameTextField.getText());
						profile.set_BackupPaths(filePathsEditorPane.getText());
						
						SaveProfile(profile);
						
						setVisible(false);
					}
				});
				saveButton.setActionCommand("OK");
				buttonPane.add(saveButton);
				getRootPane().setDefaultButton(saveButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}		
	}
	
	public void LoadBackupProfile(int backupProfileIndex)
	{
		BackupProfileIndex = backupProfileIndex;
		this.setVisible(true);

		if (backupProfileIndex > 0)
		{
			List<BackupProfile> profiles = AppConfig.getBackupProfiles();
			this.LoadProfile(profiles.get(backupProfileIndex - 1));
		}
		else
		{
			this.ClearProfile();
		}
	}
	
	private void LoadProfile(BackupProfile profile)
	{
		this.accountTypeComboBox.setSelectedItem("Amazon S3");
		this.bucketNameTextField.setText(profile.get_Bucket());
		this.userNameTextField.setText(profile.get_Username());
		this.passwordtextField.setText(profile.get_Password());
		this.filePathsEditorPane.setText(profile.get_BackupPaths().replace(";", "\n"));
	}
	
	private void ClearProfile()
	{
		this.accountTypeComboBox.setSelectedItem("Amazon S3");
		this.bucketNameTextField.setText("");
		this.userNameTextField.setText("");
		this.passwordtextField.setText("");
		this.filePathsEditorPane.setText("");
	}
	
	private static void SaveProfile(BackupProfile profile)
	{
		// for when saving new profile
		if (BackupProfileIndex <= 0)
		{
			profile.set_BackupProfileIndex(AppConfig.getBackupProfiles().size());
		}
		
		AppConfig.Save(profile);
	}
}
