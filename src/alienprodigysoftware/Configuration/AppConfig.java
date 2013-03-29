package alienprodigysoftware.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConfig 
{
	private Properties _settingsFile;
	private String _settingsPath;
	
	private String getSettingsPath()
	{
		return this._settingsPath;
	}
	
	private Properties getSettingsFile()
	{
		if (this._settingsFile == null)
		{
			this._settingsFile = new Properties();
			 
	    	try 
	    	{
	           //load a properties file
	    		this._settingsFile.load(new FileInputStream(getSettingsPath()));
	    	} 
	    	catch (FileNotFoundException ex)
	    	{
	    		this._settingsFile = this.CreateConfigurationFile();
	    	}
	    	catch (IOException ex) 
	    	{
	    		ex.printStackTrace();
	        }
		}
		
		return this._settingsFile;
	}
	
	private Properties CreateConfigurationFile()
	{
		Properties prop = new Properties();
		 
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
	
	public AppConfig(String settingsPath)
	{
		this._settingsPath = settingsPath;
	}
	
	public String GetProperty(String propertyName)
	{
		return getSettingsFile().getProperty(propertyName);
	}
}
