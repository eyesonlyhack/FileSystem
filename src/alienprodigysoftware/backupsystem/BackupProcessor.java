package alienprodigysoftware.backupsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import alienprodigysoftware.Configuration.AppConfig;
import alienprodigysoftware.Interfaces.IFileStore;
import alienprodigysoftware.filemanagement.AmazonFileStore;
import alienprodigysoftware.filemanagement.LocalFileStore;

public class BackupProcessor
{
	private AppConfig _appconfig;
	
	private AppConfig getAppConfig()
	{
		if (this._appconfig == null)
		{
			this._appconfig = new AppConfig("config.properties");
		}
		
		return this._appconfig;
	}
	
	public void ProcessAmazonS3Backup()
	{	
		List<String> directories = new ArrayList<String>();
		
		String[] directoriesArray = this.getAppConfig().GetProperty("syncfolders").split(";");
		List list = Arrays.asList(directoriesArray);
		
		directories.addAll(list);
		
		String buckedName = this.getAppConfig().GetProperty("amazonbucketbucket");
		String amazonAccessKeyID = this.getAppConfig().GetProperty("amazonaccesskeyid");
		String amazonSecretKey = this.getAppConfig().GetProperty("amazonsecretaccesskey");
		
		IFileStore localFileStore = new LocalFileStore(directories);
		IFileStore amazonS3FileStore = new AmazonFileStore(buckedName, amazonAccessKeyID, amazonSecretKey);
		
		localFileStore.BackupTo(amazonS3FileStore);
	}
}
