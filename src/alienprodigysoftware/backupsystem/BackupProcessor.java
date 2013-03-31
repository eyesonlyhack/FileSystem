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
	public void ProcessAmazonS3Backup()
	{	
		List<String> directories = new ArrayList<String>();
		
		String[] directoriesArray = AppConfig.GetProperty("syncfolders").split(";");
		List list = Arrays.asList(directoriesArray);
		
		directories.addAll(list);
		
		String buckedName = AppConfig.GetProperty("amazonbucketbucket");
		String amazonAccessKeyID = AppConfig.GetProperty("amazonaccesskeyid");
		String amazonSecretKey = AppConfig.GetProperty("amazonsecretaccesskey");
		
		IFileStore localFileStore = new LocalFileStore(directories);
		IFileStore amazonS3FileStore = new AmazonFileStore(buckedName, amazonAccessKeyID, amazonSecretKey);
		
		localFileStore.BackupTo(amazonS3FileStore);
	}
}
