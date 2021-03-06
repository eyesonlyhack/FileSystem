package alienprodigysoftware.filemanagement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

import alienprodigysoftware.Interfaces.IFileStore;

public class LocalFileStore implements IFileStore
{
	private List<String> _directories;
	
	private List<String> getDirectories()
	{
		return this._directories;
	}
	
	private void setDirectories(List<String> directories)
	{
		this._directories = directories;
	}
	
	public LocalFileStore(List<String> directories)
	{
		setDirectories(directories);
	}
	
	@Override
	public boolean BackupTo(IFileStore fileStore)
	{
		int failedUploadCount = 0;
		int successfullUploadCount = 0;
				
		for (File file : this.FilesList())
		{
			String localFileHash = this.GetFileHash(file.getAbsolutePath());
			String externalFileHash = fileStore.GetFileHash(file.getAbsolutePath());
			
			// debug line
			System.out.println("local: " + localFileHash);
			System.out.println("external: " + externalFileHash);
					
			// Check if file already exists 
			if (localFileHash != externalFileHash)
			{
				if (fileStore.AddFile(file))
				{
					successfullUploadCount++;
				}
				else
				{
					failedUploadCount++;
					System.out.println("Failed Upload for: " + file.getAbsolutePath());
				}
			}
		}
		
		System.out.println("Failed Uploads: " + failedUploadCount);
		System.out.println("Successfull Uploads: " + successfullUploadCount);
		
		return false;
	}

	private List<File> FilesList()
	{
		List<File> returnFilesList = new ArrayList<File>();
		
		for (String directory : getDirectories())
		{
			for (File file : GetFilesList(directory))
			{
				returnFilesList.add(file);
			}
		}
		
		return returnFilesList;
	}

	private static File FetchFile(String filePath)
	{
		File file = new File(filePath);		
		return file;
	}
	
	private static List<File> GetFilesList(String directoryPath)
	{
		List<File> filesList = new ArrayList<File>();
		
		File folder = FetchFile(directoryPath);

		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++)
		{			
			
			if (listOfFiles[i].isDirectory())
			{
				filesList.addAll(GetFilesList(listOfFiles[i].getAbsolutePath()));
			}
			else
			{
				filesList.add(listOfFiles[i]);
			}
		}
		
		return filesList;
	}

	@Override
	public String GetFileHash(String filePath)
	{
		String md5Hex = null;
		
		try
		{
			File localFile = FetchFile(filePath);
			
			HashCode localFileMD5 = Files.hash(localFile, Hashing.md5());
	        md5Hex = localFileMD5.toString();
		}
		catch (Exception e)
        {
        	System.out.println(e.getMessage());
        }
		
		return md5Hex;
	}

	@Override
	public boolean AddFile(File file)
	{
		// TODO Auto-generated method stub
		return false;
	}
}
