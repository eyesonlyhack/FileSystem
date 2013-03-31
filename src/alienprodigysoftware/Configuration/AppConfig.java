package alienprodigysoftware.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AppConfig 
{
	private static Properties _settingsFile;
	
	private static String getSettingsPath()
	{
		return "config.properties";
	}
	
	private static Properties getSettingsFile()
	{
		if (_settingsFile == null)
		{
			_settingsFile = new Properties();
			 
	    	try 
	    	{
	           //load a properties file
	    	   _settingsFile.load(new FileInputStream(getSettingsPath()));
	    	} 
	    	catch (FileNotFoundException ex)
	    	{
	    		_settingsFile = CreateConfigurationFile();
	    	}
	    	catch (IOException ex) 
	    	{
	    		ex.printStackTrace();
	        }
		}
		
		return _settingsFile;
	}
	
	private static Properties CreateConfigurationFile()
	{
		// fetch existing file
		Properties prop = getSettingsFile();
		 
    	try 
    	{
    		//set the properties value
    		prop.setProperty("syncfolders", "");
    		prop.setProperty("amazonaccesskeyid", "");
    		prop.setProperty("amazonsecretaccesskey", "");
    		prop.setProperty("amazonbucketbucket", "");
    		//save properties to project root folder
    		prop.store(new FileOutputStream(getSettingsPath()), null);

			return prop;
    	} 
    	catch (IOException ex) 
    	{
    		ex.printStackTrace();
        }
    	
    	return null;
	}
	
	private static void SaveConfigurationFile(BackupProfile profile)
	{
		Properties prop = new Properties();
		 
    	try 
    	{
    		if (profile.get_BackupProfileIndex() == 1)
    		{
	    		//set the properties value
	    		prop.setProperty("syncfolders", profile.get_BackupPaths().replace("\n", ";"));
	    		prop.setProperty("amazonaccesskeyid", profile.get_Username());
	    		prop.setProperty("amazonsecretaccesskey", profile.get_Password());
	    		prop.setProperty("amazonbucketbucket", profile.get_Bucket());
    		}
    		else
    		{
    			//set the properties value
	    		prop.setProperty(profile.get_BackupProfileIndex() + "syncfolders", profile.get_BackupPaths().replace("\n", ";"));
	    		prop.setProperty(profile.get_BackupProfileIndex() + "amazonaccesskeyid", profile.get_Username());
	    		prop.setProperty(profile.get_BackupProfileIndex() + "amazonsecretaccesskey", profile.get_Password());
	    		prop.setProperty(profile.get_BackupProfileIndex() + "amazonbucketbucket", profile.get_Bucket());
    		}
    		
    		//save properties to project root folder
    		prop.store(new FileOutputStream(getSettingsPath()), null);   
    	} 
    	catch (IOException ex) 
    	{
    		ex.printStackTrace();
        }
    	
    	// need to reload settings
    	_settingsFile = null;
	}

	public static String GetProperty(String propertyName)
	{
		return getSettingsFile().getProperty(propertyName);
	}
	
	public static List<BackupProfile> getBackupProfiles()
	{
		List<BackupProfile> backupProfiles = new ArrayList<BackupProfile>();
		
		// get values from config file
		BackupProfile config = new BackupProfile();
		config.set_BackupPaths(getSettingsFile().getProperty("syncfolders"));
		config.set_Username(getSettingsFile().getProperty("amazonaccesskeyid"));
		config.set_Password(getSettingsFile().getProperty("amazonsecretaccesskey"));
		config.set_Bucket(getSettingsFile().getProperty("amazonbucketbucket"));
		
		backupProfiles.add(config);
		
		return backupProfiles;
	}

	public static void Save(BackupProfile profile)
	{
		SaveConfigurationFile(profile);
	}
}
